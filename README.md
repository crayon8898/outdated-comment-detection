# README

The replication package of paper *Are your comments outdated? Towards automatically detecting code-comment consistency*.

## Download resources

- Download data sets and other related resources from [here](https://drive.google.com/drive/folders/1ako_cqz_ERIYlE7yK0TLZd-zZhQG-du6?usp=sharing)

## Installation

```
#enviroment requirements.yaml
```

```bash
conda env create -f requirements.yaml
```



## View the extracted features

```
features_max_paper.csv, these are the sample features we used in the paper. Here we calculate the similarity between token and sentence through max function.

The following two features are extracted after we greatly expanded the data set after the paper submission. Because the data set is greatly expanded and the positive and negative samples are unbalanced, the results are reduced. Use sum function and max function respectively to calculate the similarity between token and sentence.

expand_features_sum.csv
expand_features_max.csv

```

## Infer and Eval

- Open outdate_ Predict folder, run the main program to detect outdated comments on all samples
- Open other folders beginning with outDatePredict, and run the main program with folders ending with corresponding classifiers to test the performance of different classifiers
- Open the outdate_predict_only_comment_change folder and run the main.py program to test RQ3 (samples with only commented changes)

## utils

Tools for data cleaning, original commit processing, feature extraction, word vector training, etc. (including CCSet extraction, change extraction, CCSet with only annotation change extraction, feature extraction, token feature extraction, sample de-duplication, and local csv generation)

**NOTE**: In our paper, each model was trained and evaluated 10 times, and the reported results are the best performance of the experiments.
So the outputs of the above commands would be different from those reported in our paper.
