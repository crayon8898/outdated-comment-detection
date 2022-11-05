import numpy as np
import scipy
import pandas as pd
from sklearn.ensemble import RandomForestClassifier
from sklearn.tree import DecisionTreeClassifier
from sklearn.metrics import accuracy_score, recall_score, f1_score, precision_score

from sklearn.model_selection import train_test_split, cross_val_score, GridSearchCV
from sklearn.metrics import roc_curve, auc, roc_auc_score
from sklearn.metrics import classification_report
from sklearn.metrics import ConfusionMatrixDisplay, confusion_matrix

import matplotlib.pyplot as plt

df = pd.read_csv("features_only_comment_change.csv")
df.dropna(inplace=True)
df.drop_duplicates(inplace=True)
df.head()
print('-----------df.RESULT.value_counts()---------------')
print(df.label.value_counts())

y = df.label
print('-----------y.head()---------------')
print(y.head())

x = df.drop('label', axis=1)
x = x.drop(['changeNum', 'attribute', 'methodDeclaration', 'methodRenaming',
            'returnType', 'parameterDelete', 'parameterInsert',
            'parameterRenaming', 'parameterTypeChange', 'containReturn',
            'TODOCount', 'FIXMECount', 'XXXCount',
            'BUGCount', 'VERSIONCount', 'FIXEDCount',
            'ifInsert', 'ifUpdate',
            'ifMove', 'ifDelete', 'forInsert', 'forUpdate',
            'forMove', 'forDelete', 'foreachInsert',
            'foreachUpdate', 'foreachMove', 'foreachDelete',
            'whileInsert', 'whileUpdate', 'whileMove', 'whileDelete',
            'catchInsert', 'catchUpdate', 'catchMove', 'catchDelete',
            'tryInsert', 'tryUpdate', 'tryMove', 'tryDelete',
            'throwInsert', 'throwUpdate', 'throwMove', 'throwDelete',
            'methodInvInsert', 'methodInvUpdate', 'methodInvMove', 'methodInvDelete', 'assignInsert',
            'assignUpdate', 'assignMove', 'assignDelete',
            'varDecInsert', 'varDecUpdate', 'varDecMove',
            'varDecDelete', 'elseInsert', 'elseUpdate', 'elseMove', 'elseDelete', 'cmt2ch_sim'], axis=1)

print('-----------x.head()---------------')

num_fea = x.dtypes[x.dtypes != 'object'].index
x[num_fea] = x[num_fea].apply(lambda a: (a - a.mean()) / (a.std()))
x[num_fea] = x[num_fea].fillna(0)
print(x.head())

seed = 10
xtrain, xtest, ytrain, ytest = train_test_split(x, y, test_size=0.3, random_state=seed)
rfc = RandomForestClassifier(n_estimators=100, criterion='gini', max_features='sqrt')

rfc = rfc.fit(xtrain, ytrain)

result = rfc.score(xtest, ytest)
print('-----------result---------------')
print(result)

print('------------所有的树--------------')
print(rfc.estimators_)

print('-----------xtest判定结果---------------')
print(rfc.predict(xtest))

print('----------roc_auc_score(ytest, rfc.predict_proba(xtest)[:, 1])----------------')

print(roc_auc_score(ytest, rfc.predict_proba(xtest)[:, 1]))

print('------------feature_importances_--------------')
importances = rfc.feature_importances_
print(rfc.feature_importances_)

print(np.argsort(importances))

std = np.std([tree.feature_importances_ for tree in rfc.estimators_], axis=0)
indices = np.argsort(importances)[::-1]
print('feature ranking')
for f in range(min(20, xtrain.shape[1])):
    print('%2d) %-*s %f' % (f + 1, 30, xtrain.columns[indices[f]], importances[indices[f]]))
plt.figure()
plt.title('feature importances')
plt.bar(range(xtrain.shape[1]), importances[indices], color='r', yerr=std[indices], align='center')
plt.xticks(range(xtrain.shape[1]), indices)
plt.xlim([-1, xtrain.shape[1]])
plt.show()

predicted = rfc.predict(xtest)

cm2 = confusion_matrix(ytest, predicted, labels=[0, 1])

tn = cm2[0][0]
fp = cm2[0][1]
fn = cm2[1][0]
tp = cm2[1][1]

pre = float(tp) / float(tp + fp)
rec = float(tp) / float(tp + fn)
f1 = 2 * pre * rec / (pre + rec)

# print(cm2)
# print(sum(predicted))
# print("过时注释:")
# print('Recall:', rec)
# print('F1-score:', f1)
# print('Precision score:', pre)

df = pd.read_csv("/Users/chenyn/chenyn's/研究生/DataSet/My/CSV/features_only_comment_change.csv")
df = df.fillna(0)

ytest = df.label
xtest = df.drop(['label', 'changeNum', 'attribute', 'methodDeclaration', 'methodRenaming',
                 'returnType', 'parameterDelete', 'parameterInsert',
                 'parameterRenaming', 'parameterTypeChange', 'containReturn',
                 'TODOCount', 'FIXMECount', 'XXXCount',
                 'BUGCount', 'VERSIONCount', 'FIXEDCount',
                 'ifInsert', 'ifUpdate',
                 'ifMove', 'ifDelete', 'forInsert', 'forUpdate',
                 'forMove', 'forDelete', 'foreachInsert',
                 'foreachUpdate', 'foreachMove', 'foreachDelete',
                 'whileInsert', 'whileUpdate', 'whileMove', 'whileDelete',
                 'catchInsert', 'catchUpdate', 'catchMove', 'catchDelete',
                 'tryInsert', 'tryUpdate', 'tryMove', 'tryDelete',
                 'throwInsert', 'throwUpdate', 'throwMove', 'throwDelete',
                 'methodInvInsert', 'methodInvUpdate', 'methodInvMove', 'methodInvDelete', 'assignInsert',
                 'assignUpdate', 'assignMove', 'assignDelete',
                 'varDecInsert', 'varDecUpdate', 'varDecMove',
                 'varDecDelete', 'elseInsert', 'elseUpdate', 'elseMove', 'elseDelete', 'cmt2ch_sim'], axis=1)

predicted = rfc.predict(xtest)
cm2 = confusion_matrix(ytest, predicted, labels=[0, 1])

tn = cm2[0][0]
fp = cm2[0][1]
fn = cm2[1][0]
tp = cm2[1][1]

pre = float(tp) / float(tp + fp)
rec = float(tp) / float(tp + fn)
f1 = 2 * pre * rec / (pre + rec)

print(cm2)
print(sum(predicted))
print("过时注释:")
print('Recall:', rec)
print('F1-score:', f1)
print('Precision score:', pre)
