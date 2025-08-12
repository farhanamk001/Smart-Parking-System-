import email
from email.mime.text import MIMEText
import smtplib
import time
from flask import Flask
# from flask_socketio import SocketIO
from threading import Thread
from database import *
from public import public
from admin import admin
from parking import park
from datetime import datetime, timedelta
from api import api

# from flask_mail import Mail
import smtplib
from email.mime.text import MIMEText 

app = Flask(__name__)
app.secret_key = "secret_key"

app.register_blueprint(public)
app.register_blueprint(admin)
app.register_blueprint(park)
app.register_blueprint(api)

# mail=Mail(app)
app.config['MAIL_SERVER']='smtp.gmail.com'
app.config['MAIL_PORT'] = 587
app.config['MAIL_USERNAME'] = 'projectsriss2020@gmail.com'
app.config['MAIL_PASSWORD'] = 'vroiyiwujcvnvade'
app.config['MAIL_USE_TLS'] = False
app.config['MAIL_USE_SSL'] = True

# socketio = SocketIO(app)


app.run(debug=True, port=5000, host="0.0.0.0")