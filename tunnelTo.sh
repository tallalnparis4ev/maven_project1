#!/bin/bash
ssh -L 6969:$1:6969 -L 6970:$1:6970 -L 9001:$1:9001 tm214@$1
