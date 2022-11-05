import os

l = []


def traverse_folder(url):
    if os.path.isdir(url):
        for f in os.listdir(url):
            traverse_folder(os.path.join(url, f))
    else:
        try:
            # print(url)
            s = ''
            with open(url) as f:
                for line in f.readlines():
                    s = s + line
                # print(s)
            l.append(s)
        except:
            pass


if __name__ == '__main__':
    traverse_folder("/changes")
    print(len(l))
    s = set(l)
    print(len(s))
    index = 0
    for t in s:
        print(t)
        print('------------------------------------------------')
        with open("/changes_drop_duplicates/" + str(index) + ".java",'w') as f:
            index += 1
            f.write(t)
