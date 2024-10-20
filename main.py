import threading
import time
from random import randint

class InternetShop:
    def __init__(self, working_hours=(9, 24)):
        self.stock = {}
        self.lock = threading.Semaphore(1)
        self.working_hours = working_hours

    def add_item(self, item_name, quantity):
        self.lock.acquire()
        if item_name in self.stock:
            self.stock[item_name] += quantity
        else:
            self.stock[item_name] = quantity
        print(f'Added {quantity} of {item_name}. Current stock: {self.stock[item_name]}')
        self.lock.release()

    def buy_item(self, item_name, quantity):
        current_hour = time.localtime().tm_hour
        if self.working_hours[0] <= current_hour < self.working_hours[1]:
            self.lock.acquire()
            if item_name in self.stock and self.stock[item_name] >= quantity:
                self.stock[item_name] -= quantity
                print(f'{quantity} {item_name}(s) purchased. Remaining stock: {self.stock[item_name]}')
            else:
                print(f'{item_name} is not available or insufficient stock.')
            self.lock.release()
        else:
            print('Store is closed!')

def admin_task(shop):
    while True:
        time.sleep(randint(1, 5))  
        shop.add_item('Laptop', randint(1, 5))

def customer_task(shop, customer_id):
    while True:
        time.sleep(randint(1, 5))  
        shop.buy_item('Laptop', randint(1, 3))
        print(f'Customer {customer_id} tried to buy.')

if __name__ == "__main__":
    shop = InternetShop()

    admin_thread = threading.Thread(target=admin_task, args=(shop,))
    admin_thread.start()

    customers = [threading.Thread(target=customer_task, args=(shop, i)) for i in range(5)]
    for customer in customers:
        customer.start()

