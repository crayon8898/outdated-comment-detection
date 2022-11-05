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

from sklearn import svm

import matplotlib.pyplot as plt
from sklearn.svm import SVC

df = pd.read_csv("../features_drop_duplicates.csv")

df.dropna(inplace=True)


# print(df.head)
df.head()
print('-----------df.RESULT.value_counts()---------------')
print(df.RESULT.value_counts())

y=df.RESULT
print('-----------y.head()---------------')
print(y.head())

x=df.drop('RESULT',axis=1)
x=x.drop('ID',axis=1)
x=x.drop('M/B_ID',axis=1)
print('-----------x.head()---------------')
print(x.head())

seed=8
xtrain,xtest,ytrain,ytest = train_test_split(x,y,test_size=0.3,random_state=seed)


# rfc = svm.SVC()
# rfc = svm.NuSVC()
rfc = svm.LinearSVC(dual=False,max_iter=1000)

rfc.fit(xtrain, ytrain)
# rfc = RandomForestClassifier(n_estimators=100,criterion='gini',max_features='auto')
# rfc = rfc.fit(xtrain,ytrain)

result = rfc.score(xtest,ytest)
print('-----------result---------------')
print(result)

# print('------------所有的树--------------')
# print(rfc.estimators_)

print('-----------xtest判定结果---------------')
print(rfc.predict(xtest))

# print('----------roc_auc_score(ytest, rfc.predict_proba(xtest)[:, 1])----------------')

# print(roc_auc_score(ytest, rfc.predict_proba(xtest)[:, 1]))

# print('------------feature_importances_--------------')
# importances=rfc.feature_importances_
# print(rfc.feature_importances_)

# print(np.argsort(importances))

# std = np.std([tree.feature_importances_ for tree in rfc.estimators_],axis=0)
# indices = np.argsort(importances)[::-1]
# print('feature ranking')
# for f in range(min(20,xtrain.shape[1])):
#     print('%2d) %-*s %f' % (f+1,30,xtrain.columns[indices[f]],importances[indices[f]]))
# plt.figure()
# plt.title('feature importances')
# plt.bar(range(xtrain.shape[1]),importances[indices],color='r',yerr=std[indices],align='center')
# plt.xticks(range(xtrain.shape[1]),indices)
# plt.xlim([-1,xtrain.shape[1]])
# plt.show()

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
