#!/usr/bin/python
import ldtp
import sys
import SimpleXMLRPCServer
import getopt
import logging
import re
log = logging.getLogger("xmlrpcserver.ldtp")

logging.getLogger().setLevel(logging.DEBUG)

port = "8000"

class AllMethods:
    pass

_supported_methods = filter(lambda s: re.match("[a-z]+", s), dir(ldtp)) #only methods with lowercase names

#create a class with all ldtp methods as attributes
for name in _supported_methods:
    setattr(AllMethods, name, getattr(ldtp, name))

def usage():
    print("Usage:\n(-p, --port=) Port to listen on\n-h This help message\n")

def start_server(port):
    server = SimpleXMLRPCServer.SimpleXMLRPCServer(('', int(port)))
    server.register_introspection_functions()
    server.register_instance(AllMethods())

    print("Listening on port %s" % port)
    server.serve_forever()

def main():
    try:
        opts, args = getopt.getopt(sys.argv[1:], "hp:v", ["help", "port="])
        print(opts)
    except getopt.GetoptError, err:
        # print help information and exit:
        print str(err) # will print something like "option -a not recognized"
        usage()
        sys.exit(2)

    port = 8001 #default port

    for o, a in opts:
        if o in ("-p", "--port"): 
            port = a
        elif o in ("-h", "--help"):
            usage()
            sys.exit()
        else:
            assert False, "unhandled option"
    start_server(port)

if __name__ == "__main__":
    main()

