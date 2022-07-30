import numpy
import numpy as np
import scipy
import pandas as pd
from sklearn.ensemble import RandomForestClassifier
from sklearn.tree import DecisionTreeClassifier
from sklearn.metrics import accuracy_score, recall_score, f1_score, precision_score

from sklearn.model_selection import train_test_split,cross_val_score,GridSearchCV
from sklearn.metrics import roc_curve,auc,roc_auc_score
from sklearn.metrics import classification_report
from sklearn.metrics import ConfusionMatrixDisplay, confusion_matrix


import matplotlib.pyplot as plt


df = pd.read_excel("/Users/crayon/Data/研究生/论文写作/Data-driven Code-Comment Consistency Detection and Analysis/实验/实验代码/getfeatures/features/Block&Method.xlsx")
dftest = pd.read_excel('/Users/crayon/Data/研究生/论文写作/Data-driven Code-Comment Consistency Detection and Analysis/实验/实验代码/OnlyCommentChange/onlyComment处理后.xlsx')

df.dropna(inplace=True)
dftest.dropna(inplace=True)

# print(df.head)
df.head()
print('-----------df.RESULT.value_counts()---------------')
print(df.RESULT.value_counts())

y=df.RESULT
y_only_comment = dftest.RESULT
print('-----------y.head()---------------')
print(y.head())

x=df.drop('RESULT',axis=1)
x=x.drop('ID',axis=1)
x=x.drop('M/B_ID',axis=1)
x_only_comment = dftest.drop('RESULT',axis=1)
x_only_comment = x_only_comment.drop('ID',axis=1)
x_only_comment = x_only_comment.drop('M/B_ID',axis=1)
print('-----------x.head()---------------')
print(x.head())

seed=8
xtrain,xtest,ytrain,ytest = train_test_split(x,y,test_size=0.3,random_state=seed)

rfc = RandomForestClassifier(n_estimators=100,criterion='gini',max_features='auto')
rfc = rfc.fit(xtrain,ytrain)



result = rfc.score(xtest,ytest)
print('-----------result---------------')
print(result)

print('------------所有的树--------------')
print(rfc.estimators_)

print('-----------xtest判定结果---------------')
print(rfc.predict(xtest))

print('----------roc_auc_score(ytest, rfc.predict_proba(xtest)[:, 1])----------------')

print(roc_auc_score(ytest, rfc.predict_proba(xtest)[:, 1]))

print('------------feature_importances_--------------')
importances=rfc.feature_importances_
print(rfc.feature_importances_)

print(np.argsort(importances))

std = np.std([tree.feature_importances_ for tree in rfc.estimators_],axis=0)
indices = np.argsort(importances)[::-1]
print('feature ranking')
for f in range(min(20,xtrain.shape[1])):
    print('%2d) %-*s %f' % (f+1,30,xtrain.columns[indices[f]],importances[indices[f]]))
plt.figure()
plt.title('feature importances')
plt.bar(range(xtrain.shape[1]),importances[indices],color='r',yerr=std[indices],align='center')
plt.xticks(range(xtrain.shape[1]),indices)
plt.xlim([-1,xtrain.shape[1]])
plt.show()

predicted = rfc.predict(xtest)
cm1 = confusion_matrix(ytest,predicted,labels=[1,0])
cm2 = confusion_matrix(ytest,predicted,labels=[0,1])
print("过时注释的混淆矩阵:")
print(cm2)

print("\n未过时注释的混淆矩阵:")

print(cm1)
print('Accuracy score:', accuracy_score(ytest, predicted))
print('Recall:', recall_score(ytest, predicted))
print('F1-score:', f1_score(ytest, predicted))
print('Precision score:', precision_score(ytest, predicted))

predicted = rfc.predict(x_only_comment)
print('只发生注释变更的过时检测结果:')
print(predicted)
print('过时的个数:')
print(numpy.sum(predicted))
print(('总个数:'))
print(len(predicted))
print('过时注释比例:')
print(float(numpy.sum(predicted))/float(len(predicted)))
print("--------------")
print(y_only_comment)
