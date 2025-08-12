import mysql.connector

username="root"
password=""
port=3306
database="smart_parking"


def select(qry, params=None):
    con = mysql.connector.connect(user=username, password=password, host="localhost", database=database, port=port)
    cur = con.cursor(dictionary=True)
    
    if params:
        cur.execute(qry, params)
    else:
        cur.execute(qry)

    result = cur.fetchall()
    cur.close()
    con.close()
    return result

def insert(qry):
	con=mysql.connector.connect(user=username,password=password,host="localhost",database=database,port=port)
	cur=con.cursor(dictionary=True)
	cur.execute(qry)
	con.commit()
	result=cur.lastrowid
	cur.close()
	con.close()
	return result




def update(qry, params=None):
    con = mysql.connector.connect(user=username, password=password, host="localhost", database=database, port=port)
    cur = con.cursor(dictionary=True)

    if params:
        cur.execute(qry, params)
    else:
        cur.execute(qry)

    # Commit the changes to the database
    con.commit()

    cur.close()
    con.close()


def delete(q):
	cnx = mysql.connector.connect(user=username, password=password, host="localhost", database=database,port=port)
	cur = cnx.cursor(dictionary=True)
	cur.execute(q)
	cnx.commit()
	result = cur.rowcount
	cur.close()
	cnx.close()