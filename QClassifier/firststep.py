#!/usr/bin/python
from QClassifier import QClassifierImpl
import nltk
import jieba.analyse
import re
from xml.etree import ElementTree
import sys
stdi,stdo,stde=sys.stdin,sys.stdout,sys.stderr
reload(sys) 
sys.setdefaultencoding('utf8')
sys.stdin,sys.stdout,sys.stderr=stdi,stdo,stde
print sys.getdefaultencoding()





#��ȡ�������� 
##testtree = ElementTree.parse(r"D:\Program Files\Python\Sample.xml")
testtree = ElementTree.parse(r"D:\QAhomework\testset\testset.xml")


#buildroot������xml��ʽ�����������stage2��ʽ�������ĵ� 
buildroot = ElementTree.Element("buildroot") 

i=0
print i
clf = QClassifierImpl(train_data_path = '../data/pair.xml')
clf.train()
print i
fo = open("testwrite.txt", "a+")
for question in testtree.iter('question'):

      testtags = jieba.analyse.extract_tags(question.getchildren ()[0].text, topK=5)
      testkeys=" ".join(testtags)
##      print testkeys
##      testtype= classifier.classify(gender_features(testkeys))
##      print testtype
  
  
#��buildroot�����ӽڵ�testquestion
      testquestion = ElementTree.SubElement(buildroot, "testquestion")  
#����testquestion�ĸ�������
      testquestion_id = ElementTree.SubElement(testquestion, "id")  
      testquestion_id.text = str(i)
##      print testquestion_id.text
      fo.write( testquestion_id.text);
      
      testquestion_q = ElementTree.SubElement(testquestion, "q")  
      testquestion_q.text = str(question.getchildren ()[0].text.decode('utf8'))
##      print testquestion_q.text
      fo.write( testquestion_q.text);
      
      testquestion_category = ElementTree.SubElement(testquestion, "category")  
##      testquestion_category.text = str(testtype)
      testquestion_category.text = clf.get_type(question.getchildren ()[0].text)
##      print testquestion_category.text
      fo.write( testquestion_category.text);
      
      testquestion_query = ElementTree.SubElement(testquestion, "query")  
      testquestion_query.text = str(testkeys.decode('utf8'))
##      print testquestion_query.text
      fo.write( testquestion_query.text);
      
##      testquestion_query.text = str(testkeys.encode('utf8'))
##      print testquestion_query.text      
      i=i+1


#������ĵ�д��uu.xml
buildtree = ElementTree.ElementTree(buildroot)  
buildtree.write("testsetstep2.xml") 






