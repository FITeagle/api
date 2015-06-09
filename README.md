[![Build Status](https://travis-ci.org/FITeagle/api.svg?branch=master)](https://travis-ci.org/FITeagle/api)

FITeagle API
============

The Core API provides the interfaces for the communication between all other modules.


Installation
------------

    mvn clean install

Config Files
------------
A lot of Adapters and the Testbed need Config-Files. These files are located under $user/.fiteagle and usually have the ending ".properties".
The Adapters save and read all necessary informations they need to run properly in these files.
Each Propertie-File is named after the owning Adapter. So if you need to change something for your Motorgarage-Adapter you have to look in the file "MotorGarage-1.properties".

The Configs are serialized in JSON, so one example Config would look like this:

            {
            "componentID": "http://localhost/resource/PhysicalNodeAdapter-1",
            "Resource_namespace": "http://localhost/resource/",
            "hostname": "localhost",
            "password": "",
            "Local_namespace": "http://localhost/",
            "ip": "127.0.0.1"
            }
 If you want to change a value you just have to write it between the two "".
 Please don't change a Key (The field on the left side from the ":" , or the Adapter won't work correctly!
 
 NOTE: If you don't have physical access to the machine or the /.fiteagle directory you can use the REST-Method shown below
    


REST-API of the Configurations
------------
If you want to update, set a new Config or a new Ontology for your running Adapter or your Testbed you can simply use the REST API each Adapter implements automatically.

With the command:

        curl -i -X POST -H 'Content-Type:application/json' -d @$your_file localhost:8080/$adapter_package/$instance_name/config
it is possible to send the Adapter a config file, which will be updated in the adapter on the fly.

Please remember that it is not possible by now to change one variable alone, so you always have to send a complete and correct Config-File.

One example command to update the SSH-Adapter:

        curl -i -X POST -H 'Content-Type:application/json' -d @$your_file localhost:8080/sshService/PhysicalNodeAdapter-1/config
    
If everything went allright you should get a "HTTP 200 OK" as an answer
