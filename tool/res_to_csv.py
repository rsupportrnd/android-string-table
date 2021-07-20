from types import GeneratorType
from typing import Generator
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
        if item.text == None:
            yield (item.attrib["name"], "")
        else:
            yield (item.attrib["name"], item.text)


def make_dict(folders: list, folders_path: str, target: str):
    """
    ## input
    ["values-en", "values-ko"], "c:\\Users"

    ## output
    {name : {lang : value}}"""

    if type(folders) is GeneratorType:
        folders = list(folders)

    retval = {}
    for lang in folders:
        for (name, value) in read_item(f"{folders_path}/{lang}/{target}"):
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

    key_list = []

    for (name, lang_value) in value_dict.items():

        if not return_header_row:
            return_header_row = True
            # ["id", "values-ko", "values-en"]
            key_list = list(lang_value.keys())
            yield ["id"] + key_list
        # ["title", "타이틀", "app title""]
        retval = [name]

        for key in key_list:
            retval.append(lang_value[key])
        yield retval


def create_csv(folders: list, folders_path: str, output: str, target: str):
    with open(output, "w") as csv:
        elements = make_dict(folders, folders_path, target)

        check_sum = 0
        for cols in read_row(elements):
            check_sum += len(cols)
            if check_sum % len(cols) != 0:
                raise "should be same column size between previous and current"

            replaced_row = map(lambda x: str.replace(x, "\n", "\\n"), cols)
            quotation_row = map(lambda x: f"\"{x}\"", replaced_row)
            str_row = reduce(lambda x, y: f"{x},{y}", quotation_row) + "\n"
            csv.write(str_row)


def get_parameters():
    import sys

    resource_path = "."
    output = "strings.csv"
    target = "strings.xml"
    previous_arg = ""
    for arg in sys.argv:
        print(arg)
        if previous_arg == "input":
            resource_path = arg
        elif previous_arg == "output":
            output = arg
        elif previous_arg == "target":
            target = arg
        previous_arg = arg
    return (resource_path, output, target)


def find_folders(path: str, target: str):
    """"""
    import os
    import sys

    for folder in filter(lambda it: it.find("values") == 0, os.listdir(path)):
        for sub_file in os.listdir(f"{path}/{folder}"):
            if sub_file == target:
                yield folder


if __name__ == "__main__":
    resource_path, output, target = get_parameters()
    folders = find_folders(resource_path, target)

    create_csv(folders, resource_path, output, target)
