#!/usr/bin/python
import ldtp
import sys
import SimpleXMLRPCServer
import getopt

port = "8000"


class AllMethods:
    click = ldtp.click


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

    port = 8000 #default port

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

