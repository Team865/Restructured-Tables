import io
import sys

import pandas as pd

from .clip import clip
from .. import tba_v3 as tba
from ..core import ft
from ..tba_v3.client import client_instance


def confirm(s):
    print(s)
    yn = input("Proceed?[y/N] ")
    return yn == "y" or yn == "Y"


def qms(args):
    if not client_instance.auth_key:
        key = ft.sys_reg_prompt("tba_key", "Enter your TBA key to continue (it will be cached): ")
        client_instance.set_key(key)
    with tba.session(overwrite_id="qms", event_key=args.event_key) as s:
        event = tba.event_helper(s)
        event.check_validity()
        if args.li:
            print(event.qualification_match_schedule(use_df=False, transpose=args.transpose))
        else:
            df = event.qualification_match_schedule(transpose=args.transpose)
            if args.csv:
                with io.StringIO() as buf:
                    df.to_csv(buf)
                    print(buf.getvalue())
            else:
                with pd.option_context('display.max_rows', None, 'display.max_columns', None):
                    print(df)


def cli_main():
    if len(sys.argv) == 1:
        clip.print_help()
        return
    args = clip.parse_args()
    if args.command == "qms":
        qms(args)
