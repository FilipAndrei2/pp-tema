import os
import sys
import re
# import sysv_ipc
from PyQt5.QtWidgets import QWidget, QApplication, QFileDialog, QTextEdit, QLineEdit
from PyQt5.uic import loadUi
from PyQt5 import QtCore


def debug_trace(ui=None):
    from pdb import set_trace
    QtCore.pyqtRemoveInputHook()
    set_trace()
    # QtCore.pyqtRestoreInputHook()


class HTMLConverter(QWidget):
    ROOT_DIR = os.path.dirname(os.path.abspath(__file__))

    def __init__(self):
        super(HTMLConverter, self).__init__()
        ui_path = os.path.join(self.ROOT_DIR, 'html_converter.ui')
        loadUi(ui_path, self)
        #message_queue = sysv_ipc.MessageQueue(-1)
        self.textbox = QTextEdit(self)
        self.textbox.move(90, 50)
        self.textbox.resize(500, 300)

        self.box = QLineEdit(self)
        self.box.move(600, 50)


        self.browse_btn.clicked.connect(self.browse)
        self.convert_btn.clicked.connect(self.convertire)
        self.send_btn.clicked.connect(self.send_m)
        # c = self.textbox.toPlainText()
        # print(c)

        self.file_path = None

    def browse(self):
        options = QFileDialog.Options()
        options |= QFileDialog.DontUseNativeDialog
        file, _ = QFileDialog.getOpenFileName(self,
                                              caption='Select file',
                                              directory='',
                                              filter="Text Files (*.txt)",
                                              options=options)
        if file:
            self.file_path = file
            self.path_line_edit.setText(file)
            return file

    def convertire(self):
        file = self.browse()
        f = open(file, 'r')
        content = []
        for line in f:
            content.append(line)
        paragraph = " "
        paragraph += "<html>\n"
        i = 0
        index = 0

        while i < len(content):
            l = content[i]
            tab = 0
            for j in range(0, len(l)):
                if l[j] == '\t':
                    tab = tab+1
            if tab > 1:
                title = " "
                for j in range(0, len(l)):
                    if l[j] != ' ' and l[j] != '\t' and l[j] != '\n':
                        title += l[j]
                paragraph += "<head>\n\t\t<title>"+title+"</title>\n</head>"
                paragraph += "\n"
                i = i+1

            else:
                  if tab == 1:
                        p = " "
                        p += l
                        i = i+1
                        s = content[i]
                        check = 1
                        while s[0] != '\t' and check == 1:
                            p += s
                            i = i+1
                            if i < len(content):
                                s = content[i]
                            else:
                                check = 0
                        paragraph += "<p"+str(index)+">"+p+"</p"+str(index)+">"
                        paragraph += "\n"
                        index = index+1

                  else:
                        if l[0] == '\n':
                            paragraph += "\n"
                            i = i+1
        paragraph += "</html>"
        # self.button.label = QLabel(paragraph, self)
        self.textbox.setPlainText(paragraph)
        return paragraph

    def content(self):
        return self.textbox.toPlainText()

    def init_convertInHTML(self):
        self.button.move(550, 50)
        self.button.resize(150, 30)
        self.button.clicked.connect(self.convertire)

    def send_m(self):
        self.convertire()
        print(self.textbox.toPlainText())
        c = self.textbox.toPlainText()
        regex = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>"
        p = re.compile(regex)
        if (c == None):
            print("Text gol")

        if re.search(p, c):
            # print("Este HTML!")
            self.box.setText("Este HTML!")
            title = input('Title for file : ')
            f = open(title, 'w')
            f.write(c)
        else:
            # print("Nu este HTML!")
            self.box.setText("Nu este HTML!")
        # send_message(message_queue, "A" )


def send_message(message_queue, message):
    message_queue.send(message)


if __name__ == '__main__':
    # message_queue = sysv_ipc.MessageQueue(-1)
    app = QApplication(sys.argv)
    window = HTMLConverter()
    window.show()
    # send_message(message_queue, msg)
    sys.exit(app.exec_())
