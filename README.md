# Playtomic

This is just an experiment. But in order to get this thing working, do:

Download and install [Datomic Free](http://downloads.datomic.com/free.html), <code>cd</code> into the installation dir and run:

    bin/transactor config/samples/free-transactor-template.properties
		
Run the <code>main</code> function in <code>models.Repository</code> (this will create the <code>playtomic</code> database and loads the schema and data).

Download and install [Play Framework](http://www.playframework.com/download), follow the installation instructions and from the <code>playtomic</code> directory, do:

    play run
		
Point your browser or HTTP client to:

    http://localhost:9000/user/list
		
Using the following credentials:
	
    admin/admin
		
If all goes well you will see something like the following:
	
    [
     {
      "id" : 17592186045418,
      "middlename" : null,
      "firstname" : null,
      "lastname" : null,
      "username" : "admin",
      "email" : null
      }
    ]