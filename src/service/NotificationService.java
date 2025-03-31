/**
 * NotificationService monitors a Bear's stats and updates a UI text field
 * with alerts if any of the stats drop below warning thresholds.
 *
 * Used to inform the player when the bear is hungry, tired, angry, or dead.
 *
 * @Author Bhavya Sharma
 */

 package service;

 import model.Bear;
 import javafx.scene.text.Text;

 /**
  * NotificationService class is responsible for monitoring the bear's status
  * and updating the UI with appropriate notifications.
  * It checks the bear's state and sets the text of a Text node to inform the user.
  */
 
 public class NotificationService {
 
     private Text notificationText;
 
     /**
      * Constructs the service with a reference to the UI text component to update.
      *
      * @param notificationText The Text node to display alerts.
      */
     public NotificationService(Text notificationText) {
         this.notificationText = notificationText;
     }
 
     /**
      * Checks the bear's current state and updates the UI with an appropriate message.
      *
      * @param bear The bear instance to evaluate.
      */
     public void checkBearStatus(Bear bear) {
         if (bear.isDead()) {
             notificationText.setText("Your bear has died!");
         } else if (bear.isAngry()) {
             notificationText.setText("Your bear is angry");
         } else if (bear.isHungry()) {
             notificationText.setText("Your bear is hungry!");
         } else if (bear.isTired()) {
             notificationText.setText("Your bear is tired");
         } else {
             notificationText.setText("Bear is calm.");
         }
     }
 }
