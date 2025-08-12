from flask import *
from database import*

park=Blueprint('park',__name__)

@park.route("/parkhome")
def park_hom():
    return render_template("parkinghome.html")

@park.route('/paidparkreg',methods=['get','post'])
def reg():

	if 'submit' in request.form:
		loc=request.form['lname']
		plac=request.form['plc']
		lat=request.form['lati']
		long=request.form['longi']
		desc=request.form['des']
		q="INSERT INTO parking_locations(location_id,location_name,place,latitude,longitude,loc_desc,type,owner_id) VALUES(NULL,'%s','%s','%s','%s','%s','paid','%s')"%(loc,plac,lat,long,desc,session['oid'])
		insert(q)
		flash('Registered Successfully...')
		return redirect(url_for('park.park_hom'))
	return render_template('paidparkreg.html')


@park.route('/parkviewpay',methods=['get','post'])
def viepay():
	
	data={}


	q="SELECT * FROM payments INNER JOIN checkin_checkout USING(checkin_checkout_id) INNER JOIN slots USING(slot_id) INNER JOIN parking_locations USING(location_id) inner join users using(user_id) WHERE owner_id='%s'"%(session['oid'])
	res=select(q)
	data['pay']=res
	
	return render_template('viewpayments.html',data=data)


@park.route("/ownscanner",methods=['get','post'])
def scanner():


	return render_template('ownscanner.html')


# @park.route("/ownprocess_qr_code", methods=['POST'])
# def process_qr_code():
#     data = request.json
#     qrCodeData = data.get('qrCodeData')
#     print("////////",qrCodeData)
#     response_data = {'status': 'success', 'message': 'Barcode data processed successfully'}
#     return jsonify(response_data)


@park.route("/ownprocess_qr_code", methods=['POST'])
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


@park.route('/viewbookings',methods=['get','post'])
def viebooking():
	
	data={}

	q="SELECT * FROM users inner join book using(user_id) inner join slots using(slot_id) inner join parking_locations using(location_id) where owner_id='%s'"%(session['oid'])
	res=select(q)
	data['vie']=res
	
	return render_template('viewbooking.html',data=data)


@park.route('/viewstatus',methods=['get','post'])
def viewstatus():
	
	data={}

	q="SELECT * FROM slots inner join parking_locations using(location_id) where owner_id='%s'"%(session['oid'])
	res=select(q)
	data['view']=res
	
	return render_template('viewslotstatus.html',data=data)


@park.route('/ownslot',methods=['get','post'])
def slotreg():

	import uuid
	
	data={}
	q="SELECT * FROM  parking_locations where owner_id='%s'"%(session['oid'])
	res=select(q)
	data['view']=res
	
	if 'submit' in request.form:
		slotdes=request.form['desc']
		loc=request.form['loc']
		amt=request.form['amt']
		q="INSERT INTO slots(slot_id,name,status,location_id,amount) VALUES(NULL,'%s','free','%s','%s')"%(slotdes,loc,amt)
		insert(q)

	return render_template('ownerslot.html',data=data)
