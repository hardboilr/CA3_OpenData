# CA3
Tobias, Sebastian & Jonas

##Notes
The currency data are somehow not persisted to the db when hosted on openshift. When running on our local db's this has not been a problem. The persisted data are only to be used when presenting "historical" currency data, which is not implemented in the application. 

Therefore this problem does not affect the hosted application, because we only present "daily" currencies. The daily currencies are cached and overwritten with "fresh" rates every 24 hours. 


