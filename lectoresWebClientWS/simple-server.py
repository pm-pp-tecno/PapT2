#!/usr/bin/env python3
"""
Servidor web simple para desarrollo
Ejecutar con: python simple-server.py
"""

import http.server
import socketserver
import os
import sys
from urllib.parse import urlparse, parse_qs

class CustomHandler(http.server.SimpleHTTPRequestHandler):
    def do_GET(self):
        # Manejar rutas específicas
        if self.path == '/gestionUsuarios':
            self.serve_jsp('gestionUsuarios.jsp')
        elif self.path == '/testSoap':
            self.serve_jsp('testSoap.jsp')
        else:
            super().do_GET()
    
    def serve_jsp(self, jsp_file):
        try:
            # Leer el archivo JSP
            with open(f'WebContent/{jsp_file}', 'r', encoding='utf-8') as f:
                content = f.read()
            
            # Simular procesamiento JSP básico
            content = content.replace('${lectores.size()}', '0')
            content = content.replace('${errorMessage}', '')
            
            self.send_response(200)
            self.send_header('Content-type', 'text/html; charset=utf-8')
            self.end_headers()
            self.wfile.write(content.encode('utf-8'))
            
        except FileNotFoundError:
            self.send_error(404, f"Archivo {jsp_file} no encontrado")
        except Exception as e:
            self.send_error(500, f"Error interno: {str(e)}")

def main():
    PORT = 8080
    
    # Cambiar al directorio del proyecto
    os.chdir(os.path.dirname(os.path.abspath(__file__)))
    
    print(f"Servidor web iniciado en puerto {PORT}")
    print(f"Accede a: http://localhost:{PORT}")
    print(f"Gestión de usuarios: http://localhost:{PORT}/gestionUsuarios")
    print("Presiona Ctrl+C para detener")
    
    with socketserver.TCPServer(("", PORT), CustomHandler) as httpd:
        try:
            httpd.serve_forever()
        except KeyboardInterrupt:
            print("\nServidor detenido")

if __name__ == "__main__":
    main()
