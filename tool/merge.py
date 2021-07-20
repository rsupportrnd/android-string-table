# import os, sys
# sys.path.append(os.path.dirname(__file__))

"""
# 설명

기존 리소스 (strings.xml)과 (strings_generated.xml)을 merge합니다.

strings_generated.xml에 (strings.xml - strings_generated.xml)를 더합니다.

"""
import csv
from types import GeneratorType
import res_to_csv as res
from functools import reduce
import sys


def get_parameters():

    resource_path = "."
    output = "merged_strings.csv"
    previous_arg = ""
    for arg in sys.argv:
        print(arg)
        if previous_arg == "input":
            resource_path = arg
        elif previous_arg == "output":
            output = arg
        previous_arg = arg
    return (resource_path, output)

def read_csv_format(folders: list, folders_path: str, target="strings.xml"):

    
    elements = res.make_dict(folders, folders_path, target)

    check_sum = 0
    for cols in res.read_row(elements):
        check_sum += len(cols)
        if check_sum % len(cols) != 0:
            raise "should be same column size between previous and current"

        replaced_row = map(lambda x: str.replace(x, "\n", "\\n"), cols)
        quotation_row = map(lambda x: f"\"{x}\"", replaced_row)
        str_row = reduce(lambda x, y: f"{x},{y}", quotation_row) + "\n"
        yield str_row

def merge_dict(folders1: list, folders2: list, folders_path: str, target1: str, target2: str):
    if type(folders1) is GeneratorType:
        folders1 = list(folders1)
    if type(folders2) is GeneratorType:
        folders2 = list(folders2)

    sum_folders = sorted(set(folders1 + folders2))

    retval = {}

    for lang1 in folders1:
        for (name, value) in res.read_item(f"{folders_path}/{lang1}/{target1}"):
            columns = dict(zip(sum_folders, [""] * len(sum_folders)))
            columns.update({lang1: value})
            retval.update({name: columns})

    for lang2 in folders2:
        for (name, value) in res.read_item(f"{folders_path}/{lang2}/{target2}"):
            columns = retval.get(name,retval.get(name, dict(zip(sum_folders, [""] * len(sum_folders)))))
            columns.update({lang2: value})
            retval.update({name: columns})

    return retval
        



def create_csv(folders1: list, folders2: list, folders_path: str, output: str, target1: str, target2: str):
    with open(output, "w") as csv_file:
        csv_writer = csv.writer(csv_file)
        
        elements = merge_dict(folders1, folders2, folders_path, target1, target2)

        check_sum = 0
        for cols in res.read_row(elements):
            check_sum += len(cols)
            if check_sum % len(cols) != 0:
                raise "should be same column size between previous and current"
            csv_writer.writerow(cols)


if __name__ == "__main__":
    resource_path, output = get_parameters()

    original_folders = res.find_folders(resource_path, "strings.xml")
    generated_folders = res.find_folders(resource_path, "strings_generated.xml")

    create_csv(original_folders, generated_folders, resource_path, output, "strings.xml", "strings_generated.xml")
