
# pip install torch==1.10.0+cu113 torchvision==0.11.1+cu113 torchaudio===0.10.0+cu113 -f https://download.pytorch.org/whl/cu113/torch_stable.html

# !pip install "opencv-python-headless<4.3"

# !pip install ArabicOcr

# from pickletools import uint8
from ArabicOcr import arabicocr
import cv2
import os
# from google.colab.patches import cv2_imshow
from PIL import Image
from com.chaquo.python import Python
import io
import numpy as np
import base64

def main(data):
	print(os.getcwd())

	decodeImage=base64.b64decode(data)
	npData=np.fromstring(decodeImage,np.uint8)

	img=cv2.imdecode(npData,cv2.IMREAD_UNCHANGED)
	pil_img=Image.fromarray(img)
	buff=io.BytesIO()
	path='C:/Users/Dell-/StudioProjects/final/app/src/main/assets/'
	#
	#
	context = Python.getPlatform().getApplication().getFilesDir()
	print(context)
	pil_img.save('/data/user/0/com.example.afinal/files/picc/nelaaaaa.png')



	# print(buff)
	image_path='/data/user/0/com.example.afinal/files/picc/nelaaaaa.png'

	image = cv2.imread(image_path)
	grayImage = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
	dst=Image.fromarray(grayImage)
	dst.save('/data/user/0/com.example.afinal/files/picc/nelaaaaa.png')


	out_image='out.jpg'
	results=arabicocr.arabic_ocr(image_path,out_image)
	print(results)
	words=[]
	for i in range(len(results)):
		word=results[i][1]
		words.append(word)
		print(words)
	# with open ('file.txt','w','utf-8')as myfile:
	# 	myfile.write(str(words))
	# img = cv2.imread('out.jpg', cv2.IMREAD_UNCHANGED)
	# cv2.imshow("arabic ocr",img)
	# cv2.waitKey(0)
	return words
