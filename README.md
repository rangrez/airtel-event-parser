 ### API's included in the project: ###
1. Get all the category of events<br/>
#### http://localhost:8080/eventparser/webapi/app/categoryevents ####
  This API will provide all the eventsName in the given eventData.txt file.<br />
  
2. Unique users who visted the site on particular date and searched <br />
#### http://localhost:8080/eventparser/webapi/app/users?category=Search&date=2018-02-05T16:16:29.209 ####
          @param name - category   value [ search | booking |payment ]
          @param name - date   value [ timestamp]

3. Unique users who dropped off from the payment page on a particular date<br />
#### http://localhost:8080/eventparser/webapi/app/users?date=2018-02-05T16:16:29.209 ####
          @param name - date   value [ timestamp]

4. Unique users who visted site at least thrice in a week
#### http://localhost:8080/eventparser/webapi/app/users/visit ####

5. Conversion percentage  of  the  unique users of the site on a particular date<br />
#### http://localhost:8080/eventparser/webapi/app/users/conversionper?date=2018-01-26T16:16:29.142 ####
   @param name - date   value [ timestamp]<br />


----------------------------------------------------------------------------------------------------------------------------------------
Assume a Web/ Mobile interface, like www.makemytrip.com, that generates event’s on each and every activity of users. Activities may include Page Visit / Ticket Search/ Hotel Search / Booking /Payment. Design and implement an application which will capture all the events( parsing event json file) and generate report using Rest API. [spring web and rest]
 You need to categorize user  based on the event type / event name / visited date / gender / userId and return the unique users  in response of the Rest Api

Input

1. Parse the given event file (find the attached file)

{"appVersion":"4.7","appPlatform":"23","evSource":2,"ipAddress":"192.168.1.8","eventType":"Visit","ua":"Dalvik\/2.1.0 (Linux; U; Android 6.0; XT1068 Build\/MPB24.65-34)","deviceId":"","userId":"","deviceNetwork":"airtel”,"osVersion":"6.0","scrResolution":"720X1184","siteId":"AKB-TRL","eventName":"Visit”,"userId":"u1”, "gender":"male”,"deviceModel":"XT1068","operationSystem":"android","deviceBrand":"motorola","cid":"1001","ts":1480657414600}


2. Expected Output <br />
  i. Create Rest Api to get all the category of events (eventName).<br />
  ii. Find all the unique users who visted the site on particular date and searched. <br />
        a. Required url:: - http://localhost:8080/app/users/?category=search&date=237245745756<br />
          @param name - category   value [ search | booking |payment ]<br />
          @param name - date   value [ timestamp]<br />
  iii. Find all the unique users who dropped off from the payment page on a particular date.<br />
        a. Required url:: - http://localhost:8080/app/users/?day=237245745756<br />
           @param name - date   value [ timestamp]<br />
  iv. Find all the unique users who visted site at least thrice in a week.<br />
  v. Find the conversion percentage  of  the  unique users of the site on a particular date.<br />
      Use Formula ( No of unique users who booked ticket/ No of unique users who visited  )* 100<br />
