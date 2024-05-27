import os

from spring_config import ClientConfigurationBuilder
from spring_config.client import SpringConfigClient

env: dict


def init_env():
    # 환경변수 불러오기
    global env
    profile = os.environ["profile"]
    config = (ClientConfigurationBuilder()
              .app_name("simulator")
              .address("http://localhost:8888")
              .profile(profile)
              .branch("master")
              .build())

    c = SpringConfigClient(config)
    env = c.get_config()


init_env()
