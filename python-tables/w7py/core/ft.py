import atexit
import hashlib
import os
import pickle
import shutil
import sys
import typing

try:
    from pathlib import Path
except ImportError:
    Path = None
_join = os.path.join
if Path:
    md_home = str(Path.home())
else:
    md_home = os.path.expanduser("~")
if sys.platform.startswith("win"):
    md_root = _join(md_home, "AppData/Local/")
elif sys.platform.startswith("darwin"):
    md_root = _join(md_home, "Library/Application Support/")
else:
    md_root = md_home
md_root = _join(md_root, "w7/")
md_id = hashlib.md5(md_home.encode()).hexdigest()
md_ucf = _join(md_root, md_id)
if not os.path.isdir(md_ucf):
    os.makedirs(md_ucf)
_root_file = md_id
_root_fp = _join(md_ucf, _root_file)
_path_file = hashlib.md5(os.getcwd().encode()).hexdigest()
_path_fp = _join(md_ucf, _path_file)
md_files = [_root_file, _path_file]
md_fp = [_root_fp, _path_fp]
if os.path.isfile(_root_fp):
    with open(_root_fp, "rb") as rf:
        md_reg = pickle.load(rf)
else:
    md_reg = {}
if os.path.isfile(_path_fp):
    with open(_path_fp, "rb") as rf:
        md_path_reg = pickle.load(rf)
else:
    md_path_reg = {}
_md_should_remove_all = False


def sys_reg_prompt(key: "str",
                   prompt: "typing.Optional[str]" = None) -> "typing.Optional[str]":
    if key in md_reg.keys():
        return str(md_reg[key])
    if prompt is None:
        return None
    val = input(prompt).strip()
    md_reg[key] = val
    return val


def clean():
    md_reg.clear()
    md_path_reg.clear()


def remove_all():
    global _md_should_remove_all
    _md_should_remove_all = True


@atexit.register
def _md_cleanup():
    if _md_should_remove_all:
        shutil.rmtree(md_root)
    else:
        with open(_root_fp, "wb") as wf:
            pickle.dump(md_reg, wf)
        with open(_path_fp, "wb") as wf:
            pickle.dump(md_path_reg, wf)
