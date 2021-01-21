import xml.etree.ElementTree as ET
from functools import reduce


def read_item(path: str):
    """
    ## return
    (name, value)
    """
    dom = ET.parse(path)
    root = dom.getroot()
    for item in root.findall("string"):
        yield (item.attrib["name"], item.text)


def make_dict(folders: list, folders_path: str):
    """
    ## input
    ["values-en", "values-ko"], "c:\\Users"

    ## output
    {name : {lang : value}}"""

    retval = {}
    for lang in folders:
        for (name, value) in read_item(f"{folders_path}/{lang}/strings.xml"):
            columns = retval.get(name, dict(zip(folders, [""] * len(folders))))
            columns.update({lang: value})
            retval.update({name: columns})
    return retval


def read_row(value_dict: dict):
    """
    ### input
    {name : {lang : value}}

    ### output
    [name, value-lang1, value-lang3, ... ...]
    """
    return_header_row = False

    for (name, lang_value) in value_dict.items():
        if not return_header_row:
            return_header_row = True
            # ["id", "values-ko", "values-en"]
            yield ["id"] + list(lang_value.keys())
        # ["title", "타이틀", "app title""]
        yield [name] + list(lang_value.values())


def create_csv(folders: list, folders_path: str, output: str):
    with open(output, "w") as csv:
        elements = make_dict(folders, folders_path)
        for cols in read_row(elements):
            replaced_row = map(lambda x: str.replace(x, "\n", "\\n"), cols)
            quotation_row = map(lambda x: f"\"{x}\"", replaced_row)
            str_row = reduce(lambda x, y: f"{x},{y}", quotation_row) + "\n"
            csv.write(str_row)

def get_parameters():
    import sys

    resource_path = "."
    output = "strings.csv"
    previous_arg = ""
    for arg in sys.argv:
        print(arg)
        if previous_arg == "input":
            resource_path = arg
        elif previous_arg == "output":
            output = arg
        previous_arg = arg
    return (resource_path, output)

def find_values_folder(path: str):
    """"""
    import os
    import sys

    folders = []

    for item in os.listdir(path):
        if item.find("values") == 0:
            for children_item in os.listdir(f"{path}/{item}"):
                if children_item == "strings.xml":
                    folders.append(item)
                    break
    return folders
    
if __name__ == "__main__":
    resource_path, output = get_parameters()
    folders = find_values_folder(resource_path)
    
    create_csv(folders, resource_path, output)
