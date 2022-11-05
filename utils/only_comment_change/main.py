import os.path
from shutil import copyfile


def read_file(filepath):
    with open(filepath) as f:
        old_comment = ''
        new_comment = ''
        old_code = ''
        new_code = ''

        reach_old_comment = False
        reach_old_code = False
        reach_new_comment = False
        reach_new_code = False

        for line in f.readlines():
            if line.startswith('oldComment:'):
                reach_old_comment = True
                continue
            if line.startswith('oldCode:'):
                reach_old_comment = False
                reach_old_code = True
                continue
            if line.startswith('newComment:'):
                reach_old_code = False
                reach_new_comment = True
                continue
            if line.startswith('newCode:'):
                reach_new_comment = False
                reach_new_code = True
                continue
            if line.startswith('startline:'):
                break

            if reach_old_comment:
                old_comment += line
            if reach_old_code:
                old_code += line
            if reach_new_comment:
                new_comment += line
            if reach_new_code:
                new_code += line

        old_comment = ''.join([ch if ch.isalpha() else ' ' for ch in old_comment.lower()])
        old_code = ''.join([ch if ch.isalpha() else ' ' for ch in old_code.lower()])
        new_comment = ''.join([ch if ch.isalpha() else ' ' for ch in new_comment.lower()])
        new_code = ''.join([ch if ch.isalpha() else ' ' for ch in new_code.lower()])
        old_comment = old_comment.strip()
        old_code = old_code.strip()
        new_comment = new_comment.strip()
        new_code = new_code.strip()

        judge = True
        import re

        r = r'[\s\S]*\d*:\d*:\d*[\s\S]*'
        if re.match(r, old_comment) or re.match(r, new_comment):
            judge = False

        old_comment = [x for x in old_comment.split(' ') if x != '']
        old_code = [x for x in old_code.split(' ') if x != '']
        new_comment = [x for x in new_comment.split(' ') if x != '']
        new_code = [x for x in new_code.split(' ') if x != '']

        b1 = not (old_comment == new_comment)
        b2 = old_code == new_code
        if b1 and b2 and judge:
            name = filepath.split('/')[-1]
            copyfile(filepath, os.path.join("/Users/chenyn/chenyn's/研究生/DataSet/My/only_comment_change/", name))


def traverse_folder(filepath):
    if os.path.isdir(filepath):
        for f in os.listdir(filepath):
            traverse_folder(os.path.join(filepath, f))
    else:
        if filepath.endswith('.java'):
            print(filepath)
            read_file(filepath)


if __name__ == '__main__':
    traverse_folder("/Users/chenyn/chenyn's/研究生/DataSet/My/CCSet")
