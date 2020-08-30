import pandas as pd
import matplotlib.pyplot as plt

n_values = [2000, 3000, 5000]
output_values = ["output2000", "output3000", "output5000"]

for d in n_values:
    cmd = 'java -jar target/ss-tp2-1.0.jar -n {:g} -o output{:g}'.format(d, d)
    print(cmd)
    #os.system(cmd)

for file in output_values:
    df = pd.read_csv('../out/' + file + '.csv')
    df = df.sort_values(by='t')
    file = file.replace('output','')
    plt.plot(df['nR'], df['t'], label=file)

plt.ylabel('Tiempo [mS]', fontsize=18)
plt.xlabel('Fracción de partículas', fontsize=18)
plt.legend(title='Cantidad de partículas')
plt.show()