from flask import*
from database import*


api=Blueprint("api",__name__)


@api.route("/user_register")
def user_register():
    data={}
    firstname=request.args['firstname']
    lastname=request.args['lastname']
    housename=request.args['housename']
    place=request.args['place']
    pincode=request.args['pincode']
    phone=request.args['phone']
    email=request.args['email']
    username=request.args['username']
    password=request.args['password']
    lati=request.args['lati']
    longi=request.args['longi']
    
    qry="insert into login values(null,'%s','%s','user')"%(username,password)
    res=insert(qry)
    
    qry1="insert into users values(null,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')"%(res,firstname,lastname,housename,place,pincode,lati,longi,phone,email)
    res1=insert(qry1)
    if res1:
        data['status']="success"
    else:
        data['status']="failed"
    
    return str(data) 

@api.route("/userlogin")
def userlogin():
    data={}
    username=request.args['username']
    password=request.args['password']
    
    qry="select * from login inner join users using(login_id) where username='%s' and password='%s'"%(username,password)
    res=select(qry)
    if res:
        data['status']="success"
        data['data']=res
    return str(data)



@api.route('/view_nearby')
def view_nearby():
    data={}
    lati=request.args['lati']
    longi=request.args['longi']
    
    q="SELECT *,(3959 * ACOS ( COS ( RADIANS('%s') ) * COS( RADIANS( latitude) ) * COS( RADIANS( longitude ) - RADIANS('%s') ) + SIN ( RADIANS('%s') ) * SIN( RADIANS(latitude ) ))) AS user_distance,CONCAT(parking_locations.location_name,' ',parking_locations.place) AS dname  FROM parking_locations    HAVING user_distance<31.068 " % (lati,longi,lati)
    res=select(q)
    if res:
        data['status']="success"
        data['data']=res
    return str(data)



@api.route('/view_slot')
def view_slot():
    data={}
    location=request.args['location']
    qry="select * from slots where location_id='%s' "%(location)
    res=select(qry)
    if res:
        data['status']="success"
        data['data']=res
    else:
        data['status']='failed'
        
    data['method']='view'
    
    return str(data)


@api.route('/user_com')
def user_com():
    data={}
    com=request.args['com']
    id=request.args['logid']
    

        
    
    qry="insert into complaint values(null,(select user_id from users where login_id='%s'),'%s',curdate(),'pending')"%(id,com)
    res=insert(qry)
    if res:
        data['status']="success"
    data['method']="send"
    return str(data)


@api.route('/view_reply')
def view_reply():
    data={}
    id=request.args['logid']

    qry="select * from complaint where user_id=(select user_id from users where login_id='%s')"%(id)
    res=select(qry)
    if res:
        data['status']="success"
        data['data']=res
    data['method']="reply"

    return str(data)


@api.route('/bookslot')
def bookslot():
    data={}
    id=request.args['id']
    number=request.args['vehiclenumber']
    slot=request.args['slotid']
    date=request.args['date']
    
    qry3="select * from book where slot_id='%s' and date='%s'"%(slot,date)
    res3=select(qry3)
    if res3:
        data['status']="already booked"
    else:   
        qry="insert into book values(null,(select user_id from users where login_id='%s'),'%s','%s','%s')"%(id,slot,number,date)
        res=insert(qry)
        
        qry2="update slots set status='reserved' where slot_id='%s'"%(slot)
        res2=update(qry2)

        data['status']="success"
    data['method']='book'
    return str(data)
    
    
@api.route('/payment_alert')
def payment_alert():
    data={}
    id=request.args['id']
    qry="SELECT * FROM checkin_checkout LEFT JOIN payments USING(checkin_checkout_id) WHERE user_id = (SELECT user_id FROM users WHERE login_id = '%s') AND status = 'occupied'"%(id)
    res=select(qry)
    if res:
        data['status']="success"
        data['data']=res
    else:
        data['status']="failed"
    qry1="SELECT * FROM checkin_checkout LEFT JOIN payments USING(checkin_checkout_id) WHERE user_id = (SELECT user_id FROM users WHERE login_id = '%s') AND status = 'free'"%(id)
    res1=select(qry1)
    if res1:
        data['status']="free"
    data['method']="payment"
    return str(data)
            
            
            
@api.route('/paymentnoti')
def paymentnoti():
    data={}
    id=request.args['id']
    qry="select * from checkin_checkout inner join slots using(slot_id) inner join parking_locations using(location_id) where user_id=(select user_id from users where login_id='%s') and checkin_checkout.status='free' and ending_date=curdate()"%(id)
    res=select(qry)
    if res:
        data['status']="success"
        data['data']=res
    else:
        data['status']="failed"
    return str(data)
    
    
@api.route('/payment')
def payment():
    data={}
    amount=request.args['amount']
    checkin=request.args['checkin']
    
    qry="insert into payments values(null,'%s','%s',curdate())"%(checkin,amount)
    res=insert(qry)
    
    qry1="update checkin_checkout set status='paid' where checkin_checkout_id='%s'"%(checkin)
    res1=update(qry1)
    if res:
        data['status']="success"
        data['data']=res1
    else:
        data['status']='failed'
    return str(data)
    
    
@api.route('/park_loc')
def park_loc():
    data={}
    qry="select * from parking_locations"
    res=select(qry)
    if res:
        data['status']="success"
        data['data']=res
    else:
        data['status']="failed"
    return str(data)