from flask import *
from database import*

public=Blueprint("public",__name__)


@public.route("/")
def home():
	data={}
	session.clear()
	return render_template("home.html",data=data)

@public.route('/login',methods=['get','post'])
def login():
	session.clear()
	if 'submit' in request.form:
		uname=request.form['uname']
		passs=request.form['passs']
		q="select * from login where username='%s' and password='%s'" %(uname,passs)
		res=select(q)
		
		if res:      
			session['lid']=res[0]['login_id']
			if res[0]['user_type']=="admin":
				flash("Logging in")			
				return redirect(url_for("admin.admin_home"))
		
					
			elif res[0]['user_type']=="Parking_Owner":
				q="select * from owner where login_id='%s'"%(res[0]['login_id'])
				res3=select(q)
				if res3:
					session['oid']=res3[0]['owner_id']
					print(session['oid'],'###############################')
					flash("Logging in")
					return redirect(url_for("park.park_hom"))
			
				
			else:		
				flash("You are Not Registered")

	return render_template("login.html")

@public.route('/userregister',methods=['get','post'])
def user():
	if 'submit' in request.form:
		fname=request.form['fname']
		lname=request.form['lname']
		hname=request.form['hname']
		place=request.form['place']
		pinc=request.form['pin']
		latitude=request.form['lat']
		longitude=request.form['lon']
		phone=request.form['pho']
		email=request.form['mail']
		user=request.form['uname']
		psw=request.form['pasw']

		qry1="INSERT INTO login VALUES(NULL,'%s','%s','user')"%(user,psw)
		res=insert(qry1)
		q="INSERT INTO `users`(`user_id`,`login_id`,`first_name`,`last_name`,`house_name`,`place`,`pincode`,`latitude`,`longitude`,`phone`,`email`) VALUES(NULL,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')"%(res,fname,lname,hname,place,pinc,latitude,longitude,phone,email)
		insert(q)
		flash('Registered Successfully...')
		return redirect(url_for('public.login'))
	return render_template('userreg.html')

@public.route('/owneregister',methods=['get','post'])
def paregister():
	if 'submit' in request.form:
		fname=request.form['fname']
		lname=request.form['lname']
		hname=request.form['hname']
		place=request.form['place']
		pinc=request.form['pin']
		latitude=request.form['lat']
		longitude=request.form['lon']
		phone=request.form['pho']
		email=request.form['mail']
		user=request.form['uname']
		psw=request.form['pasw']
		q="INSERT INTO `login`(`username`,`password`,`user_type`) VALUES('%s','%s','Parking_Owner')"%(user,psw)
		logid=insert(q)
		q="INSERT INTO `owner`(`owner_id`,`login_id`,`first_name`,`last_name`,`house_name`,`place`,`pincode`,`latitude`,`longitude`,`phone`,`email`) VALUES(NULL,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')"%(logid,fname,lname,hname,place,pinc,latitude,longitude,phone,email)
		insert(q)
		flash('Registered Successfully...')
		return redirect(url_for('public.login'))
	return render_template('ownereg.html')


import uuid
from demo_video import veh
from absl import flags

flags.DEFINE_string('parking_video', '', 'path to input video')
flags.DEFINE_string('output', 'results/output1.avi', 'path to save results')
flags.DEFINE_integer('size', 608, 'resize images to')



@public.route('/parkingvdeo',methods=['get','post'])
def parkingvdeo():
	if 'submit' in request.form:
		video = request.files['video']
		# Generate a unique filename
		filename = str(uuid.uuid4()) + video.filename
		# Save the uploaded video file
		video_path = 'parking_video/' + filename
		print(video_path,"///////////////////")
		video.save(video_path)
  
		v=veh(video_path)
		print(v,"-----------------")
		
	return render_template("parking_video.html")



@public.route('/exitvdeo',methods=['get','post'])
def exitvdeo():
	if 'submit' in request.form:
		video = request.files['video']
		# Generate a unique filename
		filename = str(uuid.uuid4()) + video.filename
		# Save the uploaded video file
		video_path = 'parking_video/' + filename
		print(video_path,"///////////////////")
		video.save(video_path)
  
		v=veh(video_path)
		print(v,"-----------------")
		

		
		
	return render_template("exitvdeo.html")