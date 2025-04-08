from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
import time

# CONFIGURACIÓN
MAX_UNFOLLOW = 5  # Cambia este número según el límite que quieras
USERNAME = "dev.jorgepinto@gmail.com"
PASSWORD = "Jorge.152Koarte"

# Iniciar navegador
driver = webdriver.Chrome()
driver.get("https://www.instagram.com/")

time.sleep(3)

# Iniciar sesión
username = driver.find_element(By.NAME, "username")
password = driver.find_element(By.NAME, "password")

username.send_keys(USERNAME)
password.send_keys(PASSWORD)
password.send_keys(Keys.RETURN)

time.sleep(5)  # Esperar que cargue el perfil

# Ir a la lista de personas que sigues
driver.get(f"https://www.instagram.com/{USERNAME}/following/")
time.sleep(5)

# Extraer nombres de personas que sigues
following = driver.find_elements(By.XPATH, "//a[contains(@href, '/')]")
following_list = [user.text for user in following if user.text != ""]

# Ir a la lista de seguidores
driver.get(f"https://www.instagram.com/{USERNAME}/followers/")
time.sleep(5)

# Extraer nombres de seguidores
followers = driver.find_elements(By.XPATH, "//a[contains(@href, '/')]")
followers_list = [user.text for user in followers if user.text != ""]

# Identificar personas que no te siguen
no_follow_back = [user for user in following_list if user not in followers_list]

print(f"Personas que no te siguen de vuelta: {no_follow_back}")

# Contador de unfollows
unfollow_count = 0

# Dejar de seguir con límite diario
for user in no_follow_back:
    if unfollow_count >= MAX_UNFOLLOW:
        print(f"Límite diario de {MAX_UNFOLLOW} unfollows alcanzado. Terminando...")
        break
    
    driver.get(f"https://www.instagram.com/{user}/")
    time.sleep(3)
    
    try:
        unfollow_button = driver.find_element(By.XPATH, "//button[text()='Siguiendo']")
        unfollow_button.click()
        time.sleep(2)
        confirm_button = driver.find_element(By.XPATH, "//button[text()='Dejar de seguir']")
        confirm_button.click()
        print(f"Dejaste de seguir a {user}")
        unfollow_count += 1
        time.sleep(3)  # Pequeña pausa para evitar bloqueos
    except:
        print(f"No se pudo dejar de seguir a {user}")

driver.quit()
