# a collection of helpful cucumber steps for any feature/scenario

Given /^I am evaluating (US[0-9]+)$/ do |user_story|
  $active_us = user_story
end
