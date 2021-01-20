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


def make_dict(folders: list):
    """
    ## input
    ["values-en", "values-ko"]

    ## output
    {name : {lang : value}}
    """

    retval = {}
    for lang in folders:
        for (name, value) in read_item(lang + "/strings.xml"):
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


def create_csv(folders: list):
    with open("strings.csv", "w") as csv:
        elements = make_dict(folders)
        for cols in read_row(elements):
            replaced_row = map(lambda x: str.replace(x, "\n", "\\n"), cols)
            quotation_row = map(lambda x: f"\"{x}\"", replaced_row)
            str_row = reduce(lambda x, y: f"{x},{y}", quotation_row) + "\n"
            csv.write(str_row)


if __name__ == "__main__":
    create_csv([
        "values", "values-en",
        "values-ko", "values-ja"
    ])
