#!/usr/bin/env python3

# Based on http://www.piware.de/2011/01/creating-an-https-server-in-python/

# generate server.xml with the following command:
#    openssl req -new -x509 -keyout key.pem -out server.pem -days 365 -nodes
# run as follows:
#    python3 simple-https-server.py
# then in your browser, visit:
#    https://localhost:4443


import http.server
import ssl
import os
import json
from urllib.parse import urlparse, parse_qs

directory_of_script = os.path.dirname(os.path.abspath(__file__))

class CustomHandler(http.server.SimpleHTTPRequestHandler):
    def do_POST(self):
        if self.path == '/log':
            content_length = int(self.headers['Content-Length'])
            post_data = self.rfile.read(content_length)
            try:
                log_data = json.loads(post_data.decode('utf-8'))
                print(f"[BROWSER CONSOLE] {log_data.get('level', 'log').upper()}: {log_data.get('message', '')}")
            except:
                print(f"[BROWSER CONSOLE] {post_data.decode('utf-8')}")
            
            self.send_response(200)
            self.send_header('Access-Control-Allow-Origin', '*')
            self.end_headers()
            self.wfile.write(b'OK')
        else:
            self.send_response(404)
            self.end_headers()
    
    def do_OPTIONS(self):
        self.send_response(200)
        self.send_header('Access-Control-Allow-Origin', '*')
        self.send_header('Access-Control-Allow-Methods', 'POST, OPTIONS')
        self.send_header('Access-Control-Allow-Headers', 'Content-Type')
        self.end_headers()

#server_address = ('localhost', 4443)
server_address = ('', 8080)
httpd = http.server.HTTPServer(server_address, CustomHandler)

# Create SSL context
ssl_context = ssl.SSLContext(ssl.PROTOCOL_TLS_SERVER)
ssl_context.load_cert_chain(
    certfile=os.path.join(directory_of_script, "server.pem"),
    keyfile=os.path.join(directory_of_script, "key.pem")
)
httpd.socket = ssl_context.wrap_socket(httpd.socket, server_side=True)

print(f"Server running on https://localhost:8080")
httpd.serve_forever()