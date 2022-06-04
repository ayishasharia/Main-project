from flask import *
from src.dbconnection import *
app = Flask(__name__)
app.secret_key="kjhhg"
@app.route('/')
def login():
    return render_template("index2.html")


@app.route('/log')
def log():
    return render_template("loginindex.html")


@app.route('/adminhome')
def adminhome():
    return render_template("adminindex.html")




@app.route('/login1',methods=['post'])
def login1():
    username=request.form['textfield']
    password=request.form['textfield2']
    qry="select * from login where user_name=%s and password=%s"
    val=(username,password)
    res=selectone(qry,val)
    if res is None:
        return '''<script>alert("invalid");window.location="/"</script>'''

    elif res[3]=="admin":
        return '''<script>alert("success");window.location="/adminhome"</script>'''
    else:
        return '''<script>alert("invalid");window.location="/"</script>'''


@app.route('/adddocs',methods=['post'])
def adddocs():
    return render_template("adddocs.html")

@app.route('/adddoc1',methods=['post'])
def adddoc1():

        firstname=request.form['textfield']
        secondname=request.form['textfield2']
        gender= request.form['radiobutton']
        qualification= request.form['textfield3']
        specification= request.form['textfield4']
        phoneno= request.form['textfield5']
        email= request.form['textfield7']
        place= request.form['textfield8']
        username=request.form['textfield6']
        password= request.form['textfield9']
        confirm = request.form['textfield10']
        if password == confirm:

            qry="insert into login values(null,%s,%s,'doctor')"
            val=(username,password)
            lid=iud(qry,val)

            qry1="insert into doctors values(null,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
            val1=(firstname,secondname,gender,qualification,specification,phoneno,email,place,str(lid))
            iud(qry1,val1)
            return '''<script>alert("success");window.location="/managedoc?"</script>'''
        else:
            return '''<script>alert("password doesn't match");window.location="/adddoc1"</script>'''

@app.route('/schedule',methods=['post'])
def schedule():
    qry="select * from doctors"
    res=selectall(qry)

    return render_template("schedule.html",val=res)









@app.route('/schedule1',methods=['post'])
def schedule1():

        doctorname=request.form['select']
        daysavailable=request.form.getlist("check")
        avail=str(','.join(daysavailable))
        fromtime= request.form['textfield2']
        totime= request.form['textfield3']

        qry="insert into schedule values(null,%s,%s,%s,%s)"
        val=(doctorname,avail,fromtime,totime)
        iud(qry,val)
        return '''<script>alert("successfully added");window.location="/manageschedule"</script>'''

@app.route('/updateschedule')
def updateschedule():
    qry="select * from doctors"
    res=selectall(qry)

    return render_template("schedule.html",val=res)
@app.route('/updateschedule1',methods=['post'])
def updateschedule1():

        daysavailable=request.form.getlist("checkbox")
        avail=str(','.join(daysavailable))
        fromtime= request.form['textfield2']
        totime= request.form['textfield3']

        qry="UPDATE `schedule` SET `days_available`=%s,`from_time`=%s,`to_time`=%s WHERE `schedule_id`=%s "
        val=(avail,fromtime,totime,session['schid'])
        iud(qry,val)
        return '''<script>alert("successfully updated");window.location="/manageschedule"</script>'''




@app.route('/manageschedule')
def manageschedule():
    qry="SELECT `doctors`.`first_name`,`second_name`,`doctors`.`specification`,`phno` ,`schedule`.* FROM `schedule` JOIN `doctors` ON `doctors`.`login_id`=`schedule`.`doctor_id`"
    res=selectall(qry)
    return render_template("manageschedule.html",val=res)
@app.route('/managedoc')
def managedoc():
    qry="select * from doctors"
    res=selectall(qry)
    return render_template("managedoc.html",val=res)


@app.route('/editdocs')
def editdocs():
    id = request.args.get('id')
    session['did']=id
    qry="select * from doctors where doctor_id=%s"
    res=selectone(qry,str(id))
    return render_template("editdocs.html",val=res)



@app.route('/editschedule')
def editschedule():
    qry="select * from doctors "
    res1=selectall(qry)
    id=request.args.get('id')
    session['schid']=id
    qry="SELECT `schedule`.*,`doctors`.`first_name`,`second_name` FROM `doctors` JOIN `schedule` ON `doctors`.`login_id`=`schedule`.`doctor_id` WHERE `schedule`.`schedule_id`=%s"
    res=selectone(qry,str(id))


    return render_template("editschedule.html",val=res,val1=res1)



@app.route('/updatedoc', methods=['post'])
def updatedoc():
    firstname = request.form['textfield']
    secondname = request.form['textfield2']
    gender = request.form['radiobutton']
    qualification = request.form['textfield3']
    specification = request.form['textfield4']
    phoneno = request.form['textfield5']
    email = request.form['textfield7']
    place = request.form['textfield8']

    qry="UPDATE `doctors` SET `first_name`=%s,`second_name`=%s,`gender`=%s,`qualification`=%s,`specification`=%s,`phno`=%s,`email`=%s,`place`=%s WHERE `doctor_id`=%s"
    val=(firstname,secondname,gender,qualification,specification,phoneno,email,place,session['did'])
    iud(qry,val)
    return '''<script>alert("successfully updated");window.location='/managedoc'</script>'''








@app.route('/deleteschedule')
def deleteschedule():
    id=request.args.get('id')
    qry="delete from schedule where schedule_id=%s"
    iud(qry,id)
    return '''<script>alert("deleted");window.location="/manageschedule"</script>'''

@app.route('/deletedoc')
def deletedoc():
    id=request.args.get('id')
    qry="delete from doctors where doctor_id=%s"
    iud(qry,str(id))
    return '''<script>alert("deleted");window.location="/managedoc"</script>'''

@app.route('/viewusers')
def viewusers():
    qry="select * from registration"
    res=selectall(qry)
    return render_template("viewusers.html",
    val=res)



app.run(debug=True)