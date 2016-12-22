# -*- coding: utf-8 -*-
import socket
import select
import time
import struct


ADDR = ('0.0.0.0', 54790)
chats = []


class Chat:
    id = 0

    def __init__(self, server_socket, open_cs):
        """
        This function create a Chat object.
        """
        self.server_socket = server_socket
        self.open_cs = open_cs
        Chat.id += 1

    def start_chat(self):
        """
         Waiting for both user to connect to the server.
         When they connect the message transferring beings.
        """
        self.conversation()

    def conversation(self):
        """
        This function transfers the messages between the clients.
        """
        try:
            while True:
                rlist, wlist, xlist = select.select(self.open_cs, self.open_cs, [])
                for sock in rlist:
                    try:
                        data = self.receive_data(sock)
                        if not data:
                            return
                        if data == 'exit':
                            self.stop_chat()
                            return
                        else:
                            for current_socket in self.open_cs:
                                if current_socket is not sock:
                                    self.send_data(current_socket, data)
                    except Exception as err:
                        self.stop_chat()
                        print "1. Original error message:\n" + str(err)
                        return
        except Exception as err:
            self.stop_chat()
            print "2. Original error message:\n" + str(err)
            return

    def stop_chat(self):
        """
        This function disconnect the server and the clients connection.
        """
        print "Closing chat #" + str(self.id) + '.'
        for sock in self.open_cs:
            sock.close()

    def receive_data(self, sock):
        """
        This function receives the data from the client socket.
        """
        try:
            # text_len = sock.recv(1024)
            # msg_len = int(text_len)
            # print msg_len
            msg_len = 1024
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
        if data:
            return data
        else:
            self.stop_chat()
            return False

    def send_data(self, sock, data):
        """
        This function sends the data to the client socket.
        """
        if data:
            sock.send(data)
            sock.send("\0")


def main():
    pass


if __name__ == '__main__':
    main()
