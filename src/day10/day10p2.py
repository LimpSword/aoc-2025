import re
import gurobipy as gp
from gurobipy import GRB

def solve(buttons, targets):
    num_buttons = len(buttons)
    num_counters = len(targets)

    # Create model
    model = gp.Model()
    model.setParam('OutputFlag', 0)  # Suppress output

    # Variables: number of times to press each button (>= 0)
    x = model.addVars(num_buttons, vtype=GRB.INTEGER, lb=0, name="x")

    # Constraints: each counter reaches its target (sum of buttons that affect it)
    for c in range(num_counters):
        model.addConstr(
            gp.quicksum(x[b] for b in range(num_buttons) if c in buttons[b]) == targets[c],
            name=f"counter_{c}"
        )

    # Objective: minimize total button presses
    model.setObjective(gp.quicksum(x[b] for b in range(num_buttons)), GRB.MINIMIZE)

    model.optimize()
    if model.status == GRB.OPTIMAL:
        return int(model.objVal)
    else:
        raise Exception("No solution found")

def parse(line):
    targets_match = re.search(r'\{(.+)}', line)
    targets = [int(x) for x in targets_match.group(1).split(',')]

    buttons = []
    for match in re.finditer(r'\((\d+(?:,\d+)*)\)', line):
        button = set(int(x) for x in match.group(1).split(','))
        buttons.append(button)

    return buttons, targets

with open("Day10_test.txt", 'r') as f:
    lines = f.readlines()

total = 0
for i, line in enumerate(lines, 1):
    buttons, targets = parse(line)
    total += solve(buttons, targets)

print(f"Total: {total}")

with open("Day10.txt", 'r') as f:
    lines = f.readlines()

total = 0
for i, line in enumerate(lines, 1):
    buttons, targets = parse(line)
    total += solve(buttons, targets)

print(f"Total: {total}")