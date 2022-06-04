from flask import *
from src.dbconnection import *
app = Flask(__name__)
app.secret_key="kjhhg"


@app.route('/login',methods=['post'])
def login():
    username = request.form['username']
    password = request.form['password']

    qry="SELECT * FROM `login` WHERE `user_name`=%s AND `password`=%s"
    val=(username,password)
    res=selectone(qry,val)

    if res is None:
        return jsonify({"task":"failed"})

    else:

        return jsonify({"task":"success","type":res[3],"id":res[0]})




@app.route('/reg',methods=['post'])
def reg():
    name = request.form['name']
    gender = request.form['gender']
    dob = request.form['dob']
    print(dob,"dob=============")
    email = request.form['email']
    housename= request.form['housename']
    phonenumber = request.form['phonenumber']
    place = request.form['place']
    pincode = request.form['pincode']
    username=request.form['username']
    password=request.form['password']
    conformpass=request.form['cpass']
    if password == conformpass:
        qry = "insert into login values(null,%s,%s,'user')"
        val = (username, password)
        lid = iud(qry,val)

        qry1 = "insert into registration values(null,%s,%s,%s,%s,%s,%s,%s,%s,%s)"

        val1 = (str(lid), name, gender, housename, place, pincode, email, phonenumber, dob )
        iud(qry1, val1)
        return jsonify({"task":"success"})

    else:
        return jsonify({"task":"failed"})


@app.route('/viewdoctors',methods=['post'])
def viewdoctors():
    qry="SELECT doctors.*,schedule.* FROM doctors JOIN SCHEDULE ON doctors.login_id=schedule.doctor_id"
    res=androidselectallnew(qry)
    print(res)
    return jsonify(res)

@app.route('/booking',methods=['post'])
def booking():
    date=request.form['date']
    u_id=request.form['u_id']
    doctor_id=request.form['doctor_id']

    qry="insert into booking values(null,%s,%s,%s,'pending')"
    val=(u_id,doctor_id,date)
    iud(qry,val)
    return jsonify({"task":"success"})
@app.route('/viewbooking',methods=['post'])
def viewbookig():
    qry="SELECT registration.*,booking.* FROM registration JOIN booking ON registration.u_id=booking.doctor_id"
    res=androidselectallnew(qry)
    print(res)
    return jsonify(res)



app.run(host="0.0.0.0",port=5000)
