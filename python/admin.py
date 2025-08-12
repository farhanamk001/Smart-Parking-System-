from flask import *
from database import*
import qrcode
from datetime import datetime, timedelta

admin=Blueprint('admin',__name__)

@admin.route("/admin_home")
def admin_home():
    data={}
    if not session.get("lid") is None:
        return render_template("admhome.html",data=data)
    else:
        return redirect(url_for("public.login"))
	

@admin.route('/parkregister',methods=['get','post'])
def register():
	current_time = datetime.now()
	print(current_time,"???????????????????????????????????????")
# Calculate new time by reducing 10 minutes
	new_time = current_time - timedelta(minutes=10)
	print(new_time,"///////////////////////////////////////////")

	if 'submit' in request.form:
		loc=request.form['lname']
		plac=request.form['plc']
		lat=request.form['lati']
		long=request.form['longi']
		desc=request.form['des']
		q="INSERT INTO parking_locations(location_id,location_name,place,latitude,longitude,loc_desc,type,owner_id) VALUES(NULL,'%s','%s','%s','%s','%s','free','0')"%(loc,plac,lat,long,desc)
		insert(q)
		flash('Registered Successfully...')
		return redirect(url_for('admin.admin_home'))
	return render_template('parkreg.html')



@admin.route('/slotregister',methods=['get','post'])
def slotreg():
	import uuid
	
	data={}
	q="SELECT * FROM  parking_locations"
	res=select(q)
	data['view']=res
	
	if 'submit' in request.form:
		slotdes=request.form['desc']
		loc=request.form['loc']
		amt=request.form['amt']
		q="INSERT INTO slots(slot_id,name,status,location_id,amount) VALUES(NULL,'%s','free','%s','%s')"%(slotdes,loc,amt)
		insert(q)

	return render_template('slotreg.html',data=data)


@admin.route('/admviewslotsta',methods=['get','post'])
def admviewsta():
	
	data={}

	q="SELECT * FROM slots inner join parking_locations using(location_id)"
	res=select(q)
	data['view']=res
	
	return render_template('admviewslotstatus.html',data=data)


@admin.route('/admviewusers',methods=['get','post'])
def admvieusers():
	
	data={}

	q="SELECT * FROM users"
	res=select(q)
	data['vie']=res
	
	return render_template('admviewusers.html',data=data)


@admin.route('/admviewbookings',methods=['get','post'])
def admviebooking():
	
	data={}

	q="SELECT * FROM users inner join book using(user_id) inner join slots using(slot_id) inner join parking_locations using(location_id)"
	res=select(q)
	data['vie']=res
	
	return render_template('admviewbooking.html',data=data)


@admin.route('/admviewpay',methods=['get','post'])
def admviepay():
	
	data={}

	q="SELECT * FROM payments inner join checkin_checkout using(checkin_checkout_id) inner join users using(user_id)"
	res=select(q)
	data['pay']=res
	
	return render_template('admviewpayments.html',data=data)

@admin.route('/admviewcom',methods=['get','post'])
def admviewcomp():

	data = {}
	qry1="select * from complaint inner join users using(user_id)"
	result=select(qry1)
	data['com']=result	

	return render_template('admviewcomp.html',data=data)


@admin.route("/admsendrep", methods=['get', 'post'])
def adm_serep():
    
		data = {}   
		compid=request.args['cid']
		if 'reply' in request.form:
			reply = request.form['rep']
			q = "UPDATE `complaint` SET `reply`='%s' WHERE `complaint_id`='%s'"%(reply,compid)
			insert(q)

			return redirect(url_for('admin.admviewcomp'))
			
		return render_template('admsendreply.html',data=data)

@admin.route("/addlaws",methods=['get','post'])
def laws():

		if 'add' in request.form:
			tit=request.form['tit']
			law=request.form['laws']
			amt=request.form['amt']

			qry="insert into law_details(law_id,title,law_details,fine_amount)values(Null,'%s','%s','%s')"%(tit,law,amt)
			insert(qry)

		return render_template('admaddlaws.html')



@admin.route("/scanner",methods=['get','post'])
def scanner():
		
		data={}
		return render_template('scanner.html',data=data)



@admin.route("/process_qr_code", methods=['POST'])
def process_qr_code():
	try:
		data = request.json
		qrCodeData = data.get('qrCodeData')
		print("////////",qrCodeData)
		session['qr']=qrCodeData
		

		qry="update booking set status='Occupied' where book_id='%s' and status='Pending'"%(qrCodeData)
		print(qry)
		update(qry)

		data={}
		qry="select * from booking inner join slots using(slot_id) inner join users using(user_id) where book_id='%s'"%(session['qr'])
		result=select(qry)
		print(result,"//////////////")
		
		data['vi']=result
	
		response_data = {'status': 'success', 'message': 'Barcode data processed successfully', 'vi': data['vi']}

		return jsonify(response_data)


	except Exception as e:

		print(f"Error processing QR code: {str(e)}")
		response_data = {'status': 'error', 'message': 'Error processing QR code'}
		return jsonify(response_data)
