from types import GeneratorType


def get_parameters():
    import sys

    resource_path = "."
    output = "strings.xlsx"
    targets = []
    previous_arg = ""
    for arg in sys.argv:
        print(arg)
        if previous_arg == "input":
            resource_path = arg
        elif previous_arg == "output":
            output = arg
        elif previous_arg == "target":
            targets.append(arg)
        previous_arg = arg

    if len(targets) == 0:
        targets = ["strings.xml"]

    return (resource_path, output, targets)


def find_folders(path: str, target: str):
    """"""
    import os
    import sys

    for folder in filter(lambda it: it.find("values") == 0, os.listdir(path)):
        for sub_file in os.listdir(f"{path}/{folder}"):
            if sub_file == target:
                yield folder


def read_item(path: str):
    """
    ## return
    (name, value)
    """
    import xml.etree.ElementTree as ET
    
    dom = ET.parse(path)
    root = dom.getroot()
    for item in root.findall("string"):
        if item.text == None:
            yield (item.attrib["name"], "")
        else:
            yield (item.attrib["name"], item.text.replace('\\"', '"').replace("\\'", "'"))


def make_dict(folders_path: str, folders_target_set: list):
    """
    ## input
    ["values-en", "values-ko"], "c:\\Users"

    ## output
    {name : {lang : value}}"""
    retval = {}
    for (folders, target) in folders_target_set:
        if type(folders) is GeneratorType:
            folders = list(folders)

        for lang in folders:
            for (name, value) in read_item(f"{folders_path}/{lang}/{target}"):
                columns = retval.get(name, dict(
                    zip(folders, [""] * len(folders))))
                columns.update({lang: value})
                retval.update({name: columns})
    return retval

def merge(resource_path: str, output: str, targets: list):
    print(f"모든 {resource_path}/values*/target을 {output}으로 병합힙니다.")
    try:
        import pandas
    except Exception as e:
        print(e)
        print("===================")
        print("pandas가 설치 되어 있지 않습니다. 'pip install pandas' or 'pip3 install pandas'")

    folders_target_set = map(lambda target: (find_folders(resource_path, target), target), targets)
    data = make_dict(resource_path, folders_target_set)
    dataFrame = pandas.DataFrame(data).T

    # 컬럼으로 정렬
    dataFrame = dataFrame[sorted(dataFrame.columns)]

    dataFrame.to_excel(output)
    print("done")


if __name__ == "__main__":
    print("====== android string table tools ========")
    print("# how to use?")
    print("     $'python merge.py input [android res path] output [output xml path] target [like strings.xml, strings_generated.xml]'")
    print("     $`python merge.py input 'src/test/java/stringtable/sample/res' target strings.xml output merged_strings.xlsx'")
    print("     $`python merge.py input 'src/test/java/stringtable/sample/res' target strings.xml target strings_generated.xml output merged_strings.xlsx'")
    resource_path, output, targets = get_parameters()
    
    merge(resource_path, output, targets)
