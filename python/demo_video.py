from flask import redirect, url_for
from database import*
import sys

import Core.utils as utils
from Core.config import cfg
from Core.yolov4 import YOLOv4, decode

from absl import app, flags
from absl.flags import FLAGS
import cv2
import numpy as np
import time

import tensorflow as tf
gpus = tf.config.experimental.list_physical_devices('GPU')

if gpus:
    try:
        tf.config.experimental.set_virtual_device_configuration(gpus[0],
            [tf.config.experimental.VirtualDeviceConfiguration(memory_limit=4096)])
    except RuntimeError as e:
        print(e)
else:
    print("No GPU found. Running on CPU.")
    
import keras_ocr
pipeline = keras_ocr.pipeline.Pipeline()

tf.keras.backend.clear_session()

STRIDES = np.array(cfg.YOLO.STRIDES)
ANCHORS = utils.get_anchors(cfg.YOLO.ANCHORS, False)
NUM_CLASS = len(utils.read_class_names(cfg.YOLO.CLASSES))
XYSCALE = cfg.YOLO.XYSCALE

def veh(path):
    FLAGS.parking_video = path

    def platePattern(string):
        if len(string) < 9 or len(string) > 10:
            return False
        elif string[:2].isalpha() == False:
            return False
        elif string[2].isnumeric() == False:
            return False
        elif string[-4:].isnumeric() == False:
            return False
        elif string[-6:-4].isalpha() == False:
            return False
        else:
            return True
        
    def drawText(img, plates):
        string  = 'plates detected :- ' + plates[0]
        for i in range(1, len(plates)):
            string = string + ', ' + plates[i]
        
        font_scale = 2
        font = cv2.FONT_HERSHEY_PLAIN

        (text_width, text_height) = cv2.getTextSize(string, font, fontScale=font_scale, thickness=1)[0]
        box_coords = ((1, 30), (10 + text_width, 20 - text_height))
        
        cv2.rectangle(img, box_coords[0], box_coords[1], (255, 255, 255), cv2.FILLED)
        cv2.putText(img, string, (5, 25), font, fontScale=font_scale, color=(0, 0, 0), thickness=2)
        
    def plateDetect(frame, input_size, model):
        frame_size = frame.shape[:2]
        image_data = utils.image_preprocess(np.copy(frame), [input_size, input_size])
        image_data = image_data[np.newaxis, ...].astype(np.float32)

        pred_bbox = model.predict(image_data)
        pred_bbox = utils.postprocess_bbbox(pred_bbox, ANCHORS, STRIDES, XYSCALE)

        bboxes = utils.postprocess_boxes(pred_bbox, frame_size, input_size, 0.25)
        bboxes = utils.nms(bboxes, 0.213, method='nms')
        
        return bboxes

    def main(_argv):
        input_layer = tf.keras.layers.Input([FLAGS.size, FLAGS.size, 3])
        feature_maps = YOLOv4(input_layer, NUM_CLASS)
        bbox_tensors = []
        for i, fm in enumerate(feature_maps):
            bbox_tensor = decode(fm, NUM_CLASS, i)
            bbox_tensors.append(bbox_tensor)
        
        model = tf.keras.Model(input_layer, bbox_tensors)
        utils.load_weights(model, 'data/YOLOv4-obj_1000.weights')
        
        vid = cv2.VideoCapture(path)
        if not vid.isOpened():
            print("Error: Unable to open video file")
            return
        
        return_value, frame = vid.read()
        if frame is None:
            print("Error: Unable to read first frame from video")
            return
        
        fourcc = cv2.VideoWriter_fourcc('F', 'M', 'P', '4')
        out = cv2.VideoWriter(FLAGS.output, fourcc, 10.0, (frame.shape[1],frame.shape[0]), True)
        if not out.isOpened():
            print("Error: Unable to open output video writer")
            return

        plates = []
        n = 0
        Sum = 0
        while True:
            start = time.time()
            n += 1
            return_value, frame = vid.read()
            if not return_value:
                print("Video capture ended")
                break
                
            if frame is None:
                print("Warning: Empty frame encountered")
                continue
                
            frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
            bboxes = plateDetect(frame, FLAGS.size, model)
            for i in range(len(bboxes)):
                img = frame[int(bboxes[i][1]):int(bboxes[i][3]), int(bboxes[i][0]):int(bboxes[i][2])]
                prediction_groups = pipeline.recognize([img])
                string = ''
                for j in range(len(prediction_groups[0])):
                    string = string+ prediction_groups[0][j][0].upper()
                    print(string,"ppppppppppppppppppppppp")
                    
                    qry="select * from book where date=curdate()"
                    res=select(qry)
                    vehicle_no=res[0]['vehicle_no']
                    slotid=res[0]['slot_id']
                    userid=res[0]['user_id']
                    
                    
                    if vehicle_no==string:    
                        qry2="select * from checkin_checkout where number_plate='%s' and starting_date=curdate()"%(vehicle_no)
                        res2=select(qry2)
                        if res2:
                            id=res2[0]['checkin_checkout_id']
                            if res2[0]['ending_date']=='pending':
                                qry3="update checkin_checkout set ending_date=curdate(),ending_time=curtime(),status='free' where checkin_checkout_id='%s'"%(id)
                                res3=update(qry3)
                                qry6="update slots set status='free' where slot_id='%s'"%(slotid)
                                update(qry6)
                                
                        else:
                            
                            
                            qry1="insert into checkin_checkout values(null,'%s','%s',curdate(),curtime(),'pending','pending','%s','occupied')"%(userid,vehicle_no,slotid)
                            insert(qry1)
                            
                            qry5="update slots set status='occupied' where slot_id='%s'"%(slotid)
                            update(qry5)


                            out.release()
                            cv2.destroyAllWindows()
                            sys.exit()

                            
                        
                        
                    
                    
                    
                    
                    
                    
                
                if platePattern(string) == True and string not in plates:
                    plates.append(string)
            
            if len(plates) > 0:
                drawText(frame, plates)
            
            frame = utils.draw_bbox(frame, bboxes)
            frame = cv2.cvtColor(frame, cv2.COLOR_RGB2BGR)
            
            for i in range(len(bboxes)):
                img = frame[int(bboxes[i][1]):int(bboxes[i][3]), int(bboxes[i][0]):int(bboxes[i][2])]
                prediction_groups = pipeline.recognize([img])
                string = ''
                for j in range(len(prediction_groups[0])):
                    string = string + prediction_groups[0][j][0].upper()
                
                if platePattern(string) == True and string not in plates:
                    plates.append(string)
            
            Sum += time.time()-start
            print('Avg fps:', Sum/n)

            out.write(frame)
            cv2.imshow("result", frame)
            if cv2.waitKey(1) == 27: break
        out.release()
        cv2.destroyAllWindows()
        
    
    app.run(main)





    

