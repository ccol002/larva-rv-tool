Below are the features which have not yet been added to the official Larva documentation

** Dynamic Clocks ** 

Dynamic Clocks allow the creation of timeouts dynamically, e.g. within a transition action. 

To listen for dynamically set timeout events, define the event in the Events section as follows: 

clockEvent = { clock@@ } 



Subsequently, the API includes the two following methods (over and above the standard Clock API)

* Adds a timeout from the current system time, i.e., if the current time is 5:30pm and a timeout is set for 60000ms, the clock event will be triggered at 5:31pm.

clock.registerDynamically(timeout).


* A more advanced method is as above, but allows the user to also set the starting time of the clock. Note that if the starting time is changed, any previously set timeouts would be cancelled.
 
clock.registerDynamically(timeout, currentTimeMillis()).