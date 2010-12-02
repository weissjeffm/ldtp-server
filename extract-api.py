#!/usr/bin/python
import inspect
from ldtpd.xmlrpc_daemon import XMLRPCLdtpd


xmlrpc = XMLRPCLdtpd()
public_apis = filter(lambda fn: fn.startswith("xmlrpc_"),  dir(xmlrpc))
names = map(lambda n: n.split("xmlrpc_")[1], public_apis) #strip prefix off fn names
specs = map(lambda n:  inspect.getargspec(getattr(xmlrpc,n)), names)
def defaultCount(spec):
  x = spec.defaults
  if (x): 
    return len(x)
  else:
    return 0
argz  = map(lambda s: [ s.args[1:], defaultCount(s)], specs)
api = zip(names, argz)
print(str(api).replace("'", '"'))
