import os
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

n_values = [1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000]
repetition = 100

for n in n_values:
    cmd = 'java -jar ../target/ss-tp2-1.0.jar -n {:g} -o output{:g} -r {:g}'.format(n, n, repetition)
    print(cmd)
    #os.system(cmd)

for n in n_values:
    df = pd.read_csv('out/r-{:g}-output{:g}.csv'.format(repetition, n))
    times = df['t'].values.tolist()
    time_average = np.average(times)
    time_std = np.std(times)
    plt.errorbar(n, time_average, yerr=time_std)
    plt.scatter(n, time_average, label=n)

plt.ylabel('Tiempo [mS]', fontsize=16)
plt.xlabel('Cantidad de partículas', fontsize=16)
plt.legend(title='Cantidad de partículas')
plt.show()
