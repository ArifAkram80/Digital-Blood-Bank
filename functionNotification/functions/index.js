


'use strict'

const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);


/*
 * 'OnWrite' works as 'addValueEventListener' for android. It will fire the function
 * everytime there is some item added, removed or changed from the provided 'database.ref'
 * 'sendNotification' is the name of the function, which can be changed according to
 * your requirement
 */

 exports.sendNotification = functions.database.ref('/notifications/{user_id}/{notification_id}').onWrite(event => {


  /*
   * You can store values as variables from the 'database.ref'
   * Just like here, I've done for 'user_id' and 'notification'
   */

  const user_id = event.params.user_id;
  const notification = event.params.notification;

  console.log('We have a notification send to : ', user_id);

  /*
   * Stops proceeding to the rest of the function if the entry is deleted from database.
   * If you want to work with what should happen when an entry is deleted, you can replace the
   * line from "return console.log.... "
   */

  if(!event.data.val()){

    return console.log('A Notification has been deleted from the database : ', notification_id);

  }
  const deviceToken = admin.database().ref(`/Users/${user_id}/device_token`).once('value');

  return deviceToken.then(result => {

  	const token_id =result.val();

  	const payload = {
        notification: {
          title : "New Blood Request",
          body: `you have a new request`,
          icon: "default"

        }
       
      };
      return admin.messaging().sendToDevice(token_id, payload).then(response => {

        console.log('This was the notification Feature');

  
      });
});
});
