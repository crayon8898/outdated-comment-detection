import pandas as pd
from numpy import loadtxt
from xgboost import XGBClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, confusion_matrix, recall_score, f1_score, precision_score

df = pd.read_csv("../features_max_paper.csv")

df.dropna(inplace=True)


# print(df.head)
df.head()
print('-----------df.RESULT.value_counts()---------------')
print(df.RESULT.value_counts())

Y=df.RESULT
print('-----------y.head()---------------')
print(Y.head())

X=df.drop('RESULT',axis=1)
X=X.drop('ID',axis=1)
X=X.drop('M/B_ID',axis=1)
print('-----------x.head()---------------')
print(X.head())

seed =7
test_size =0.33
X_train, X_test, y_train, y_test = train_test_split(X, Y, test_size=test_size, random_state=seed)

# loss
#model = XGBClassifier()
#model.fit(X_train, y_train)可视化测试集的
##loss
model = XGBClassifier()
eval_set =[(X_test, y_test)]
model.fit(X_train, y_train, early_stopping_rounds=10, eval_metric="logloss", eval_set=eval_set, verbose=False)

y_pred = model.predict(X_test)

predictions =[round(value)for value in y_pred]
accuracy = accuracy_score(y_test, predictions)

print("Accuracy: %.2f%%"%(accuracy *100.0))



cm1 = confusion_matrix(y_test,predictions,labels=[1,0])
cm2 = confusion_matrix(y_test,predictions,labels=[0,1])
print("过时注释的混淆矩阵:")
print(cm2)
print("\n未过时注释的混淆矩阵:")
print(cm1)
print('Accuracy score:', accuracy_score(y_test, predictions))
print('Recall:', recall_score(y_test, predictions))
print('F1-score:', f1_score(y_test, predictions))
print('Precision score:', precision_score(y_test, predictions))
