import pandas as pd
import os
import matplotlib.pyplot as plt

n_values = [1000, 2000, 5000]
output_values = ["output1000", "output2000", "output5000"]

for d in n_values:
    cmd = 'java -jar target/ss-tp2-1.0.jar -n {:g} -o output{:g}'.format(d, d)
    print(cmd)
    #os.system(cmd)

for file in os.listdir('../out/'):
    if file.endswith('.csv'):
        df = pd.read_csv('../out/' + file)
        df = df.sort_values(by='t')
        file = file.replace('.csv', '')
        file = file.replace('output','')
        plt.plot(df['nR'], df['t'], label=file)

plt.ylabel('Tiempo [mS]', fontsize=18)
plt.xlabel('Particulas Lado Derecho', fontsize=18)
plt.legend(title='Cantidad de particulas')
plt.show()