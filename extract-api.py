#!/usr/bin/python
import inspect
from ldtpd.xmlrpc_daemon import XMLRPCLdtpd


xmlrpc = XMLRPCLdtpd()
public_apis = filter(lambda fn: fn.startswith("xmlrpc_"),  dir(xmlrpc))
names = map(lambda n: n.split("xmlrpc_")[1], public_apis) #strip prefix off fn names
argz  = map(lambda f: inspect.getargspec(getattr(xmlrpc,f)).args[1:], public_apis)
api = zip(names, argz)
print(api)
