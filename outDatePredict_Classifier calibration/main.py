import numpy as np
import scipy
import pandas as pd
from sklearn.ensemble import RandomForestClassifier
from sklearn.tree import DecisionTreeClassifier
from sklearn.metrics import accuracy_score, recall_score, f1_score, precision_score
from sklearn import linear_model

from sklearn.model_selection import train_test_split,cross_val_score,GridSearchCV
from sklearn.metrics import roc_curve,auc,roc_auc_score
from sklearn.metrics import classification_report
from sklearn.metrics import ConfusionMatrixDisplay, confusion_matrix
from sklearn import naive_bayes

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


rfc = RandomForestClassifier(n_estimators=100,criterion='gini',max_features='sqrt')
# rfc = rfc.fit(xtrain,ytrain)


# svc = svm.LinearSVC(dual=False,max_iter=1000)
from sklearn.svm import LinearSVC

class NaivelyCalibratedLinearSVC(LinearSVC):
    """LinearSVC with `predict_proba` method that naively scales
    `decision_function` output."""

    def fit(self, X, y):
        super().fit(X, y)
        df = self.decision_function(X)
        self.df_min_ = df.min()
        self.df_max_ = df.max()

    def predict_proba(self, X):
        """Min-max scale output of `decision_function` to [0,1]."""
        df = self.decision_function(X)
        calibrated_df = (df - self.df_min_) / (self.df_max_ - self.df_min_)
        proba_pos_class = np.clip(calibrated_df, 0, 1)
        proba_neg_class = 1 - proba_pos_class
        proba = np.c_[proba_neg_class, proba_pos_class]
        return proba

# svc.fit(xtrain, ytrain)
svc = NaivelyCalibratedLinearSVC(C=1.0,dual=False,max_iter=1000)

gnb = naive_bayes.GaussianNB()
# gnb.fit(xtrain, ytrain)
#
lr = linear_model.LogisticRegression(max_iter=10000,multi_class='ovr')
# lr.fit(xtrain, ytrain)


from xgboost import XGBClassifier
class NaivelyCalibratedXGBoost(XGBClassifier):
    """LinearSVC with `predict_proba` method that naively scales
    `decision_function` output."""

    def fit(self, X, y):
        eval_set = [(xtest, ytest)]
        super().fit(X, y,early_stopping_rounds=10, eval_metric="logloss", eval_set=eval_set, verbose=False)
        df = self.decision_function(X)
        self.df_min_ = df.min()
        self.df_max_ = df.max()

    def predict_proba(self, X):
        """Min-max scale output of `decision_function` to [0,1]."""
        df = self.decision_function(X)
        calibrated_df = (df - self.df_min_) / (self.df_max_ - self.df_min_)
        proba_pos_class = np.clip(calibrated_df, 0, 1)
        proba_neg_class = 1 - proba_pos_class
        proba = np.c_[proba_neg_class, proba_pos_class]
        return proba
xgb = XGBClassifier()


from sklearn import tree
tcf = tree.DecisionTreeClassifier()


clf_list = [
    (lr, "Logistic"),
    (gnb, "Naive Bayes"),
    (svc, "SVC"),
    (rfc, "Random forest"),
    (tcf,"Decision tree"),
    (xgb,"XGBoost")
]

import matplotlib.pyplot as plt
from matplotlib.gridspec import GridSpec

fig = plt.figure(figsize=(10, 10))
gs = GridSpec(6, 2)
colors = plt.cm.get_cmap("Dark2")

from sklearn.calibration import CalibrationDisplay

ax_calibration_curve = fig.add_subplot(gs[:2, :2])
calibration_displays = {}
for i, (clf, name) in enumerate(clf_list):
    clf.fit(xtrain, ytrain)
    display = CalibrationDisplay.from_estimator(
        clf,
        xtest,
        ytest,
        n_bins=10,
        name=name,
        ax=ax_calibration_curve,
        color=colors(i),
    )
    calibration_displays[name] = display

ax_calibration_curve.grid()
ax_calibration_curve.set_title("Calibration plots")
# Add histogram
grid_positions = [(2, 0), (2, 1), (3, 0), (3, 1) ,(4,0),(4,1)]
for i, (_, name) in enumerate(clf_list):
    row, col = grid_positions[i]
    ax = fig.add_subplot(gs[row, col])

    ax.hist(
        calibration_displays[name].y_prob,
        range=(0, 1),
        bins=10,
        label=name,
        color=colors(i),
    )
    ax.set(title=name, xlabel="Mean predicted probability", ylabel="Count")

plt.tight_layout()
plt.show()
