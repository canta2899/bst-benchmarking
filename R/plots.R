library(ggplot2)
library(tidyverse)
library(readxl)

# Creazione del csv dal xlsx

time <- read_csv("./results/results.csv")

time <- time %>%
#  subset(select=-c(BS, AVL, RB)) %>%
  gather(key = "tree_type", value = "ex_time", -size) %>%
  mutate(ex_time = as.numeric(ex_time) / 10^9)

ggplot(data = time, aes(x = size, y = ex_time, color = tree_type)) +
  geom_point() +
  geom_line() +
  stat_smooth(method="gam") +
  labs(y = "Execution time") +
  scale_x_continuous(breaks = scales::pretty_breaks(n = 10)) +
  scale_y_continuous(breaks = scales::pretty_breaks(n = 10)) +
  theme(legend.position = c(.10, .90), legend.background = element_rect(fill = "transparent"), legend.title=element_blank())
ggsave("./results/medians.png")

ggplot(data = time, aes(x = size, y = ex_time, color = tree_type)) +
  geom_point() +
  geom_line() +
  stat_smooth(method="gam") +
  labs(y = "Execution time") +
  scale_x_log10() +
  scale_y_log10() +
  theme(legend.position = c(.10, .90), legend.background = element_rect(fill = "transparent"), legend.title=element_blank())
ggsave("./results/medians_log.png")
