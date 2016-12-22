# -*- coding: utf-8 -*-
from chat import Chat
import socket
import select
import sys
from threading import Thread


ADDR = ('0.0.0.0', 54890)


class Waiting:
    def __init__(self, server_socket):
        self.server_socket = server_socket
        self.open_cs = {}
        self.chats = []

    def wait_for_connection(self):
        """
        This function waits for both users' connection to the server.
        """
        self.server_socket.bind(ADDR)
        self.server_socket.listen(sys.maxint)
        print 'Waiting for connection . . .'
        while len(self.open_cs) < 2:
            rlist, wlist, xlist = select.select([self.server_socket], [], [])
            for sock in rlist:
                if sock is self.server_socket:
                    new_socket, addr = self.server_socket.accept()
                    print 'New client connected from ip ' + str(addr)
                    pref = new_socket.recv(1024)
                    pref = pref.split(',')
                    topic, side = pref[0], pref[1]

                    print '\nTopic: ' + str(topic) +'\nSide: ' + str(side)

                    if topic in self.open_cs:
                        if side != self.open_cs[topic][0][1]:
                            temp = self.open_cs[topic][0][0]
                            del self.open_cs[topic][0]
                            if len(self.open_cs[topic]) == 0:
                                del self.open_cs[topic]
                            self.match([new_socket, temp])
                    else:
                        self.open_cs.setdefault(topic, []).append((new_socket, side))

    def receive_data(self, sock):
        """
        This function receives the data from the client socket.
        """
        try:
            text_len = sock.recv(1024)
            msg_len = int(text_len)
            print msg_len
            data = sock.recv(msg_len)
            print data
        except ValueError:
            data = ""
            temp = sock.recv(1024)
            while temp != "":
                data += temp
                temp = sock.recv(1024)
        except Exception as err:
            data = 'Error getting message.\nOriginal error message:\n' + str(err)
        return data

    def match(self, sockets):
        """
        This function create a chat for
        """
        new_chat = Chat(self.server_socket, sockets)
        self.chats.append(new_chat)
        thread = Thread(target = new_chat.start_chat)
        thread.start()


def main():
    """
    Add Documentation here
    """
    server_socket = socket.socket()
    waiting_server = Waiting(server_socket)
    waiting_server.wait_for_connection()


if __name__ == '__main__':
    main()