# README

The replication package of paper *Are your comments outdated? Towards automatically detecting code-comment consistency*.

## Dataset

- Download the dataset from [here](https://drive.google.com/file/d/1pZdO-sRt2W7KgEqW3nn-6AY35Niobe46/view?usp=sharing)

## Installation

```
#enviroment requirements.yaml
```

```bash
conda env create -f requirements.yaml
```

## Infer and Eval using trained models

```bash
python outDatePredict_RandomForest/Block.py
python outDatePredict_RandomForest/Method.py
python outDatePredict_RandomForest/Block&Method.py
python outDatePredict_RandomForest/Code.py
python outDatePredict_RandomForest/Comment.py
python outDatePredict_RandomForest/Relation.py
```

**NOTE**: In our paper, each model was trained and evaluated 10 times, and the reported results are the best performance of the experiments.
So the outputs of the above commands would be different from those reported in our paper.

