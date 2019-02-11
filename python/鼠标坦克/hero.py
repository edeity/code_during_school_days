#_*_coding:utf-8_*_
'''
Created on 2013��9��22��

@author: javer
'''
'''���ӵ����������'''
from substance import *
from weapons import *
import image_factory
#    --------------------------------���ǻ����ķָ���------------------------------------------------------------------------------------
'''ͼƬ��ַ'''
tank_image_filename = 'image/heros/tank.png'
ice_halo_image_filename = 'image/heros/halos/ice_halo.png'
poisoning_halo_image_filename = 'image/heros/halos/poisoning_halo.png'
fire_halo_image_filename = 'image/heros/halos/fire_halo.png'
armor_halo_image_filename = 'image/heros/halos/armor_halo.png'
life_halo_image_filename = 'image/heros/halos/life_halo.png'
'''ͼƬ������'''
image_factory = image_factory.ImageFactory()
'''�õ�ͼƬ����'''
tank_image = image_factory.image_process(tank_image_filename, 1)
ice_halo = image_factory.image_process(ice_halo_image_filename, 1)[0]
poisoning_halo = image_factory.image_process(poisoning_halo_image_filename, 1)[0]
fire_halo = image_factory.image_process(fire_halo_image_filename, 1)[0]
armor_halo = image_factory.image_process(armor_halo_image_filename, 1)[0]
life_halo = image_factory.image_process(life_halo_image_filename, 1)[0]
#    --------------------------------���ǻ����ķָ���------------------------------------------------------------------------------------
class  WorldBridge():
    proManager = None
    Substance = None
    def __init__(self, proManager):
            WorldBridge.proManager = proManager
            
class Tank(Substance):      #Ӣ��:̹��
    def __init__(self):
        Substance.__init__(self, WorldBridge.proManager.heros, tank_image)      
        self.name = 'tank'
        self.ap = 2
        self.armor = 1
        self.life = 10
        self.speed = 4.0
        self.rotation_speed = 0.36             
        self.position = Vector2(200, 200)
        #������
        self.isMove = 0                            #������
        self.equipments = [Mine, SimpleShell, IceShell, SpecialShell, Tracking]
        self.equipment_kind = 0
        self.equipment =  self.equipments[0]        #װ�ص�����
        self.shellNum = 100                       #��ҩ����
        self.ready_to_shoot = True                   #�жϵ�ҩװ�����
        #��ʱʵ���������Ի����������Ϣ,��������ʱһ��Ҫ�޸�
        temp_equipment = self.equipment(self)
        self.equipment_name = temp_equipment.name
        self.equipment_ap = temp_equipment.ap
        temp_equipment.die()
        #��������������ɫ
        self.life_color = (255, 0, 0)#red
        self.speed_color = (44, 197, 44)#green
        self.armor_color = (29, 29, 167)#blue
        self.tank_ap_color = (237, 218, 41)#yellow
        self.weapon_ap_color = (174, 39, 182)#purple
        self.shellNum_color = (255, 255, 255)#black
    def prepare_move(self):
        #̹����ת�Ƕ��������(�����ƶ�����),̹�˵���ת�ٶȾ���
        self.rotation_position -= pygame.mouse.get_rel()[0]  * self.rotation_speed
        Substance.prepare_move(self)
    def shoot(self):
        '''���'''
        self.equipment(self)
        self.shellNum -= 1
    def equipment_change(self):#����װ��
        temp_equipment = self.equipment(self)
        self.equipment_name = temp_equipment.name
        self.equipment_ap = temp_equipment.ap
        temp_equipment.die()
    def render_statu(self):#�����Ҫ����
        #Ŀǰ��״̬
        life_rect = ((1200, 205), (self.life * 8, 15))
        pygame.draw.rect(WorldBridge.proManager.screen, self.life_color, life_rect)       
        speed_rect = ((1200, 235), (self.speed * 10, 15))
        pygame.draw.rect(WorldBridge.proManager.screen, self.speed_color, speed_rect)   
        armor_rect = ((1200, 270), (self.armor * 10, 15))
        pygame.draw.rect(WorldBridge.proManager.screen, self.armor_color, armor_rect)
        tank_ap_rect = ((1200, 300), (self.ap * 10, 15))
        pygame.draw.rect(WorldBridge.proManager.screen, self.tank_ap_color, tank_ap_rect)    
        weapon_ap_rect = ((1200, 330), (self.equipment_ap * 10, 15))
        pygame.draw.rect(WorldBridge.proManager.screen, self.weapon_ap_color, weapon_ap_rect)
        shellNum_rect = ((1200, 360), (self.shellNum, 15))
        pygame.draw.rect(WorldBridge.proManager.screen, self.shellNum_color, shellNum_rect)
        
    def render_halo(self):#0��������״̬, 1�������״̬, 2�����ж�״̬, 3����, 4�ӻ���, 5������
        if self.statu == 0:
            pass
        elif self.statu == 1:
            WorldBridge.proManager.screen.blit(ice_halo, (self.position.x - 60, self.position.y - 60))
        elif self.statu == 2:
            WorldBridge.proManager.screen.blit(poisoning_halo, (self.position.x - 60, self.position.y - 60))
        elif self.statu == 3:
            WorldBridge.proManager.screen.blit(fire_halo, (self.position.x - 50, self.position.y - 50))
        elif self.statu == 4:
            WorldBridge.proManager.screen.blit(armor_halo, (self.position.x - 50, self.position.y - 50))
        elif self.statu == 5:
            WorldBridge.proManager.screen.blit(life_halo, (self.position.x - 55, self.position.y - 55))
    
    def run(self):
        self.render_halo()
        Substance.run(self)