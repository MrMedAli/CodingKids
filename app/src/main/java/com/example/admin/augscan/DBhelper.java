package com.example.admin.augscan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;

    //Classe table
    private static final String CLASS_TABLE_NAME="CLASS_TABLE";
    public static final String CLASS_ID="CID";
    public static final String CLASS_NAME_KEY="CLASS_NAME";
    public static final String SUBJECT_NAME_KEY="SUBJECT_NAME";


    private static final String CREATE_CLASS_TABLE= "CREATE TABLE " + CLASS_TABLE_NAME + "(" + CLASS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
            CLASS_NAME_KEY+" TEXT NOT NULL,"+
            SUBJECT_NAME_KEY+" TEXT NOT NULL,"+
            "UNIQUE ("+ CLASS_NAME_KEY+","+SUBJECT_NAME_KEY+")"+");";
    private static final String DROP_CLASS_TABLE=
            "DROP TABLE IF EXISTS "+ CLASS_TABLE_NAME;
    private static final String SELECT_CLASS_TABLE="SELECT * FROM "+CLASS_TABLE_NAME;



    //Student table
    private static final String STUDENT_TABLE_NAME="STUDENT_TABLE";
    public static final String STUDENT_ID="STUDENT_ID";
    public static final String STUDENT_NAME_KEY="STUDENT_NAME";
    public static final String STUDENT_LASTNAME_KEY="STUDENT_LASTNAME";
    public static final String STUDENT_QR_CODE="STUDENT_QR_CODE";
    public static final String STUDENT_PHONE_NUMBER_P="STUDENT_PHONE_NUMBER_P";
    public static final String STUDENT_PHONE_NUMBER_M="STUDENT_PHONE_NUMBER_M";




    private static final String CREATE_STUDENT_TABLE= "CREATE TABLE " + STUDENT_TABLE_NAME + "(" + STUDENT_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
            STUDENT_NAME_KEY+" TEXT NOT NULL,"+
            STUDENT_LASTNAME_KEY+" TEXT NOT NULL,"+
            STUDENT_QR_CODE+" TEXT NOT NULL,"+
            STUDENT_PHONE_NUMBER_P+" TEXT NOT NULL, "+
            STUDENT_PHONE_NUMBER_M+" TEXT NOT NULL, "+
            "UNIQUE ("+ STUDENT_NAME_KEY+","+STUDENT_LASTNAME_KEY+")"+");";
    private static final String DROP_STUDENT_TABLE=
            "DROP TABLE IF EXISTS "+ STUDENT_TABLE_NAME;
    private static final String SELECT_STUDENT_TABLE="SELECT * FROM "+STUDENT_TABLE_NAME;


    //List table
    private static final String LIST_TABLE_NAME="LIST_TABLE_NAME";
    public static final String LIST_ID="LIST_ID";
    public static final String LIST_STUDENT_Name="LIST_STUDENT_Name";
    public static final String STUDENT_ROLL_KEY="STUDENT_ROLL";


    private static final String CREATE_LIST_TABLE= "CREATE TABLE " + LIST_TABLE_NAME + "(" + LIST_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
            CLASS_ID+" INTEGER NOT NULL,"+
            STUDENT_ID+" INTEGER NOT NULL,"+
            LIST_STUDENT_Name+" TEXT NOT NULL,"+
            STUDENT_ROLL_KEY+" INTEGER, "+
            " FOREIGN KEY ( "+ CLASS_ID+") REFERENCES "+ CLASS_TABLE_NAME+"("+ CLASS_ID+")"+", "+
            " FOREIGN KEY ( "+ STUDENT_ID+") REFERENCES "+ STUDENT_TABLE_NAME+"("+ STUDENT_ID+")"+");";
    private static final String DROP_LIST_TABLE=
            "DROP TABLE IF EXISTS "+ LIST_TABLE_NAME;
    //private static final String SELECT_LIST_TABLE="SELECT "+STUDENT_TABLE_NAME+"."+STUDENT_LASTNAME_KEY+", "+LIST_TABLE_NAME+"."+STUDENT_ROLL_KEY+", "+LIST_TABLE_NAME+"."+CLASS_ID+" FROM "+STUDENT_TABLE_NAME+", "+LIST_TABLE_NAME+" WHERE "+ STUDENT_TABLE_NAME+"."+STUDENT_ID+"="+LIST_TABLE_NAME+"."+STUDENT_ID;

    //Status table
    public static final String STATUS_TABLE_NAME="STATUS_TABLE";
    public static final String STATUS_ID="STATUS_ID";
    public static final String DATE_KEY="STATUS_DATE";
    public static final String STATUS_KEY="STATUS";


    private static final String CREATE_STATUS_TABLE= "CREATE TABLE " + STATUS_TABLE_NAME + "(" + STATUS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
            LIST_ID+" INTEGER NOT NULL,"+
            CLASS_ID+" INTEGER NOT NULL,"+
            DATE_KEY+" DATE NOT NULL,"+
            STATUS_KEY+" TEXT NOT NULL,"+
            "UNIQUE ("+ LIST_ID+","+DATE_KEY+"),"+
            " FOREIGN KEY ( "+ LIST_ID+") REFERENCES "+ LIST_TABLE_NAME+"("+ LIST_ID+"),"+
            " FOREIGN KEY ( "+ CLASS_ID+") REFERENCES "+ CLASS_TABLE_NAME+"("+ CLASS_ID+")"+");";
    private static final String DROP_STATUS_TABLE=
            "DROP TABLE IF EXISTS "+ STATUS_TABLE_NAME;
    private static final String SELECT_STATUS_TABLE="SELECT * FROM "+STATUS_TABLE_NAME;

    public DBhelper(@Nullable Context context) {
        super(context, "CodingKIDS.db", null, VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CLASS_TABLE);
        db.execSQL(CREATE_LIST_TABLE);
        db.execSQL(CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_STATUS_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        try {
            db.execSQL(DROP_CLASS_TABLE);
            db.execSQL(DROP_STUDENT_TABLE);
            db.execSQL(DROP_STATUS_TABLE);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    long addClass(String className, String subjectName){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(CLASS_NAME_KEY,className);
        values.put(SUBJECT_NAME_KEY,subjectName);

        return  database.insert(CLASS_TABLE_NAME, null,values);
    }


    Cursor getClassTable(){
        SQLiteDatabase database = this.getWritableDatabase();

        return database.rawQuery(SELECT_CLASS_TABLE,null);
    }
    Cursor getClassTablestudent(long cid){
        SQLiteDatabase database = this.getWritableDatabase();


        return database.query(CLASS_TABLE_NAME,null, CLASS_ID+"=?", new String[]{String.valueOf(cid)},null,null,null);
    }


    int deleteClass(long ClassId){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.delete(CLASS_TABLE_NAME,CLASS_ID+"=?",new String[]{String.valueOf(ClassId)});

    }
    long updateClass(long classID, String className, String subjectName){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(CLASS_NAME_KEY,className);
        values.put(SUBJECT_NAME_KEY,subjectName);

        return  database.update(CLASS_TABLE_NAME, values,CLASS_ID+"=?" , new String[]{String.valueOf(classID)});
    }

    long addStudent(String StudentName, String StudentLName, String QrCode, String PhoneNumber1 , String PhoneNumber2){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(STUDENT_NAME_KEY,StudentName);
        values.put(STUDENT_LASTNAME_KEY,StudentLName);
        values.put(STUDENT_QR_CODE,QrCode);
        values.put(STUDENT_PHONE_NUMBER_P,PhoneNumber1);
        values.put(STUDENT_PHONE_NUMBER_M,PhoneNumber2);

        return  database.insert(STUDENT_TABLE_NAME, null,values);
    }

    Cursor getStudentTable(){
        SQLiteDatabase database = this.getWritableDatabase();

        return database.rawQuery(SELECT_STUDENT_TABLE,null);
    }
    String getStudentTable_Name(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        String SELECT_STUDENt_ID= "SELECT * "+ " FROM "+ STUDENT_TABLE_NAME+ " WHERE "+ STUDENT_ID +"=" + id;
        Cursor cursor= database.rawQuery(SELECT_STUDENt_ID,null);
        cursor.moveToNext();
        String name = cursor.getString(cursor.getColumnIndex(STUDENT_NAME_KEY));
        return name;
    }
    String getStudentTable_LName(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        String SELECT_STUDENt_ID= "SELECT * "+ " FROM "+ STUDENT_TABLE_NAME+ " WHERE "+ STUDENT_ID +"=" + id;
        Cursor cursor= database.rawQuery(SELECT_STUDENt_ID,null);
        cursor.moveToNext();
        String name = cursor.getString(cursor.getColumnIndex(STUDENT_LASTNAME_KEY));
        return name;
    }
    long getStudentTable_ID(String qr){
        SQLiteDatabase database = this.getWritableDatabase();
        String SELECT_STUDENt_ID= "SELECT * "+ " FROM "+ STUDENT_TABLE_NAME+ " WHERE "+ STUDENT_QR_CODE +"=" + qr;
        Cursor cursor= database.rawQuery(SELECT_STUDENt_ID,null);
        cursor.moveToNext();
        long id = cursor.getLong(cursor.getColumnIndex(STUDENT_ID));
        return id;
    }

    int deleteStudent(long StudentId){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.delete(STUDENT_TABLE_NAME,STUDENT_ID+"=?",new String[]{String.valueOf(StudentId)});

    }
    int deleteStudent_QR(String qr){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.delete(STUDENT_TABLE_NAME,STUDENT_QR_CODE+"=?",new String[]{String.valueOf(qr)});

    }
    long updateStudent(long StudentId, String studentName, String studentLName, String PhoneNumber1 , String PhoneNumber2){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(STUDENT_NAME_KEY,studentName);
        values.put(STUDENT_LASTNAME_KEY,studentLName);
        values.put(STUDENT_PHONE_NUMBER_P,PhoneNumber1);
        values.put(STUDENT_PHONE_NUMBER_M,PhoneNumber2);

        return  database.update(STUDENT_TABLE_NAME, values,STUDENT_ID+"=?" , new String[]{String.valueOf(StudentId)});
    }
    long getStudentId(String codeQr){
        SQLiteDatabase database = this.getWritableDatabase();
        String SELECT_STUDENt_ID= "SELECT * "+ " FROM "+ STUDENT_TABLE_NAME+ " WHERE "+ STUDENT_QR_CODE +"=" + codeQr;
        Cursor cursor = database.rawQuery(SELECT_STUDENt_ID,null);
        if (((cursor != null) && (cursor.getCount() > 0)))
        {cursor.moveToNext();
            long id = cursor.getInt(cursor.getColumnIndex(STUDENT_ID));
            return id;}
        return -1;

    }
    Cursor getStudentQR(String codeQr){
        SQLiteDatabase database = this.getWritableDatabase();


        return database.query(STUDENT_TABLE_NAME,null, STUDENT_QR_CODE+"=?", new String[]{codeQr},null,null,null);
    }

    long addList(long cid, long sid, int roll, String name){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(CLASS_ID,cid);
        values.put(STUDENT_ID,sid);
        values.put(STUDENT_ROLL_KEY,roll);
        values.put(LIST_STUDENT_Name,name);

        return  database.insert(LIST_TABLE_NAME, null,values);
    }

    Cursor getListTable(long cid){
        SQLiteDatabase database = this.getWritableDatabase();
        String SELECT_LIST_TABLE="SELECT * FROM "+ LIST_TABLE_NAME;

        return database.query(LIST_TABLE_NAME,null, CLASS_ID+"=?", new String[]{String.valueOf(cid)},null,null,STUDENT_ROLL_KEY);
    }
    Cursor getListTablesid(long sid){
        SQLiteDatabase database = this.getWritableDatabase();
        String SELECT_LIST_TABLE="SELECT * FROM "+ LIST_TABLE_NAME;

        return database.query(LIST_TABLE_NAME,null, STUDENT_ID+"=?", new String[]{String.valueOf(sid)},null,null,STUDENT_ROLL_KEY);
    }
    int deleteList(long ListId){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.delete(LIST_TABLE_NAME,LIST_ID+"=?" ,new String[]{String.valueOf(ListId)});

    }
    int deleteList_StudentId(long SId){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.delete(LIST_TABLE_NAME,STUDENT_ID+"=?" ,new String[]{String.valueOf(SId)});

    }
    int deleteList_ClassId(long CId){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.delete(LIST_TABLE_NAME,CLASS_ID+"=?" ,new String[]{String.valueOf(CId)});

    }
    long updateList(long ListID, String roll){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(STUDENT_ROLL_KEY,roll);

        return  database.update(LIST_TABLE_NAME, values,LIST_ID+"=?" , new String[]{String.valueOf(ListID)});
    }
    long updateList_studentname(long sid, String studentname){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(LIST_STUDENT_Name,studentname);

        return  database.update(LIST_TABLE_NAME, values,STUDENT_ID+"=?" , new String[]{String.valueOf(sid)});
    }

    long addStatus(long lid, long cid ,String date, String status){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LIST_ID,lid);
        values.put(CLASS_ID,cid);
        values.put(DATE_KEY,date);
        values.put(STATUS_KEY,status);
        return  database.insert(STATUS_TABLE_NAME, null , values);
    }

    long updateStatus(long ListID, String date, String status){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(STATUS_KEY,status);;
        String WhereClause = DATE_KEY + "='"+date+"' AND "+LIST_ID +"="+ListID;
        return  database.update(STATUS_TABLE_NAME, values,WhereClause , null);
    }

    String getStatus(long lid, String date){
        String status=null;
        SQLiteDatabase database = this.getReadableDatabase();
        String WhereClause = DATE_KEY + "='"+date+"' AND "+LIST_ID +"="+lid;
        Cursor cursor = database.query(STATUS_TABLE_NAME, null, WhereClause, null,null,null,null);
        if (cursor.moveToFirst())
            status = cursor.getString(cursor.getColumnIndex(STATUS_KEY));
        return status;

    }
    Cursor getDistinctMonths(long cid){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.query(STATUS_TABLE_NAME,new String[]{DATE_KEY},CLASS_ID+"="+cid,null,"substr(+"+DATE_KEY+",4,7)",null,null);
    }

}
