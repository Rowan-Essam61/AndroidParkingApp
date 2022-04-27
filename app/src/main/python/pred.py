#!/usr/bin/env python
# coding: utf-8


import contextlib
import csv
import datetime
import os
import pymysql
import numpy as np
import pandas as pd
from sklearn.tree import DecisionTreeRegressor
import mysql.connector
import urllib
from datetime import date
import urllib.request
import json
def weekend_of_date(day):

    if day == "Fri" or day=="fri":
        return 'Yes'
    if day =="Thu" or day=="thu":
        return 'Yes'
    else:
        return 'No'
def season_of_date(date):
    doy = date.timetuple().tm_yday
    print(doy)

    seasons = {'spring': range(80, 172),
               'summer': range(172, 264),
                'autumn': range(264, 355)}
    if doy in seasons['spring']:
        return 'Spring'
    if date in seasons['summer']:
        return 'Summer'
    if date in seasons['autumn']:
       return 'Autumn'
    else:
        return 'Winter'


def main(id,day,time):

    print(id)
    print(day)
    print(time)
    with urllib.request.urlopen("http://192.168.220.207:8000/api/registration/prediction/"+str(id)) as url:
        s = url.read()

        # I'm guessing this would output the html source code ?
    print(type(s))
    data = json.loads(s)
    print(type(data["user"]))
    print(data["user"][0]["date"])
    y=[]
    for i in data["user"]:
        print(i)
        x=(i["date"],i["day"],i["HOUR(checkin_time)"],i["COUNT(*)"])
        y.append(x)
    z=tuple(y)
    print(z)



    output_file = '/data/user/0/com.example.afinal/files/picc/neww.csv'
    with open(output_file, 'w', newline='') as csvfile:
        csv_writer = csv.writer(csvfile, lineterminator='\n')
        csv_writer.writerow(["date","day","time","count"])
        csv_writer.writerows(z)
    dataset = pd.read_csv('/data/user/0/com.example.afinal/files/picc/neww.csv')
    dataset["date"]=pd.to_datetime(dataset["date"])
    dataset['season'] = dataset.date.map(season_of_date)
    dataset['weekend'] = dataset.day.map(weekend_of_date)
    print(dataset)
    dataset.to_csv('/data/user/0/com.example.afinal/files/picc/out3.csv', index=False)
    dataset1 = pd.read_csv('/data/user/0/com.example.afinal/files/picc/out3.csv')
    dataset1
    file1 = open("/data/user/0/com.example.afinal/files/picc/out3.csv", "a")
    file2 = open("/data/user/0/com.example.afinal/files/picc/reg.csv", "r")

    for line in file2:
       file1.write(line)

    file1.close()
    file2.close()
    dataset = pd.read_csv('/data/user/0/com.example.afinal/files/picc/out3.csv')
    d = {'Spring': 2, 'Summer': 3, 'Autumn': 4, 'Winter':1}
    dataset['season'] = dataset['season'].map(d)
    d2 = {'Yes': 1, 'No': 0}
    dataset['weekend'] = dataset['weekend'].map(d2)
    d3 = {'sun': 0, 'mon': 1, 'tue': 2, 'wed':3, 'thu':4, 'fri':5, 'sat':6 ,
         'Sun': 0, 'Mon': 1, 'Tue': 2, 'Wed':3, 'Thu':4, 'Fri':5, 'Sat':6}
    dataset['day'] = dataset['day'].map(d3)

    print(dataset)
    features = ['day', 'time', 'weekend','season']
    X = dataset[features]
    y = dataset['count']
    regressor = DecisionTreeRegressor(random_state=0)
    regressor.fit(X,y)

    x=d3.get(day)
    print(x)
    today = date.today()
    season=season_of_date(today)
    season=d.get(season)
    print(season)
    weekend=weekend_of_date(day)
    weekend=d2.get(weekend)
    print(weekend)
    y_pred = regressor.predict([[x, time,weekend,season]])
    print(y_pred[0])
    return int(y_pred[0])
