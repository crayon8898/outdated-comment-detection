import math
import os
import random

import numpy as np
import torch
import dltools
from matplotlib import pyplot as plt


# 读取数据集
# def read_txt():
#     data_dir = ''
#     # /home/chenyinan/DataSet/My/语料库/yuliao_changes.txt
#     with open('/home/chenyinan/DataSet/My/语料库/yuliao_changes.txt') as f:
#         raw_text = f.read()
#         sentences = [sentence.replace('\n', '') for sentence in raw_text.split('.') if
#                      (sentence.replace('\n', '')) != ' ' and
#                      sentence.replace('\n', '') != '']
#         res = []
#         for sentence in sentences:
#             res.append([word for word in sentence.split()])
#         return res
#
#
# sentences = read_txt()
# print('sentences------------')
# print(len(sentences))
# print(sentences[:6])

from gensim.models import Word2Vec

# model = Word2Vec(sentences=sentences, vector_size=300, window=7, min_count=1, sg=1, negative=5)
#
# model.save('word_vector.model')

model=Word2Vec.load('word_vector.model')


sim=model.wv.similarity('Info','user')
print(sim)
